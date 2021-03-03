import domain.Department;
import domain.Employee;
import domain.SalaryGrade;
import domain.SalaryGrades;
import domain.enums.EmployeeJob;
import dto.DeptJobCountDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ScottTest {

  private static EntityManager entityManager;

  @BeforeAll
  public static void initialize() {
    EntityManagerFactory entityManagerFactory =
        Persistence.createEntityManagerFactory("hello");
    entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();
    Scott scott = new Scott();

    // Test 데이터를 Insert 한다.
    scott.initialize(entityManager);
    transaction.commit();
  }

  @Test
  public void setupGradeTest() {
    // 로그가 정확히 보이지 않은 현상을 해결하기 위해
    // join fetch 로 부서 정보를 한번에 가져온다.
    String sql = "select e from Employee e join fetch e.department";
    List<Employee> employees = entityManager.createQuery(sql, Employee.class)
        .getResultList();

    sql = "select g from SalaryGrade g";
    List<SalaryGrade> grades = entityManager.createQuery(sql, SalaryGrade.class)
        .getResultList();
    SalaryGrades salaryGrades = new SalaryGrades(grades);

    // 모든 직원의 Salgrade 를 설정한다.
    employees.forEach(e -> {
      e.setGrade(salaryGrades);
      System.out.println(e);
    });

    // Scott 의 급여를 올려 Salgrade 가 변한 것을 확인해본다.
    // 기존 급여 : 3000 / 등급 : 4 등급
    // 변경 급여 : 5000 / 등급 : 5 등급
    sql = "select e from Employee e join fetch e.department where e.name = 'SCOTT'";
    Employee scott = entityManager.createQuery(sql, Employee.class)
        .getSingleResult();

    scott.changeSalary(5000d);
    System.out.printf("%s's salary grade is %d", scott.getName(),
        scott.getSalaryGrade().getGrade());

    Assertions.assertEquals((int) scott.getSalaryGrade().getGrade(), 5);
  }

  @Test
  public void getEmployeeUpstreamHierarchy() {
    Employee starter = entityManager.find(Employee.class, 7839L);

    // 주의! 재귀 호출 method 실행.
    System.out.println(starter);
    starter.printMember(starter, 0);
  }

  @Test
  public void getEmployeeDownStreamHierarchy() {
    Employee starter = entityManager.find(Employee.class, 7876L);

    // 주의!! 재귀 호출 method 실행 부분
    System.out.println(starter);
    starter.printManager(starter, 0);
  }


  @Test
  public void innerJoin_Stream() {
    // 부서이름, Job, 급여를 DTO 객체로 조회해서
    // Stream 으로 평균을 계산한다.
    String sql = "select new dto.DeptJobCountDto(d.name, e.job, e.salary) from Department d join d.employees e";
    List<DeptJobCountDto> resultList = entityManager.createQuery(sql, DeptJobCountDto.class)
        .getResultList();

    Map<String, Map<EmployeeJob, Double>> results = resultList.stream()
        .collect(Collectors.groupingBy(DeptJobCountDto::getDepartmentName,
            Collectors.groupingBy(DeptJobCountDto::getJob, Collectors.averagingDouble(DeptJobCountDto::getAmount))));

    for (String department : results.keySet()) {
      Map<EmployeeJob, Double> employeeJobDoubleMap = results.get(department);
      for (EmployeeJob employeeJob : employeeJobDoubleMap.keySet()) {
        int amount = (int) Math.round(employeeJobDoubleMap.get(employeeJob));
        int amountSize = amount / 100;

        System.out.printf("%10s | %10s : ", department,
            employeeJob);

        for (int i = 0; i < amountSize; i++) {
          System.out.print("*");
        }

        System.out.println();
      }
    }
  }

  @Test
  public void innerJoin_JPA_Association_Stream() {
    // 부서정보 조회는 JPA 연관관계로 불러온다.
    // 평균는 Stream 으로 계산.
    String sql = "select e from Employee e";
    List<Employee> employees = entityManager.createQuery(sql, Employee.class)
        .getResultList();

    Map<Department, Map<EmployeeJob, Double>> results = employees.stream()
        .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.groupingBy(Employee::getJob,
            Collectors.averagingDouble(Employee::getSalary))));

    for (Department department : results.keySet()) {
      Map<EmployeeJob, Double> employeeJobDoubleMap = results.get(department);
      for (EmployeeJob employeeJob : employeeJobDoubleMap.keySet()) {
        int amount = (int) Math.round(employeeJobDoubleMap.get(employeeJob));
        int amountSize = amount / 100;

        System.out.printf("%10s | %10s : ", department.getName(),
            employeeJob);

        for (int i = 0; i < amountSize; i++) {
          System.out.print("*");
        }

        System.out.println();
      }
    }
  }


  @Test
  public void outerJoin_Stream() {
    // SQL의 Outer Join을 Stream으로 구현한다.
    // 첫번째 시도 (실패)
    // * 부서 Stream -> 부서별 직원 Stream 호출 -> Collectors.groupingBy
    // * 실패이유 : Operation 부서가 '부서별 직원 Stream 호출' 때 사라진다.
    //            직원 Stream의 null이면 inner join과 같은 효과가 나탄다.
    // 두번째 시도 (성공)
    // * 부서 List + 부서,Job 별 평균급여.
    String sql;
    sql = "select d from Department d";
    List<Department> depts = entityManager.createQuery(sql, Department.class)
        .getResultList();

    sql = "select e from Employee e";
    List<Employee> employees = entityManager.createQuery(sql, Employee.class)
        .getResultList();

    Map<Department, Map<EmployeeJob, Double>> results = employees.stream()
        .collect(Collectors.groupingBy(Employee::getDepartment,
            Collectors.groupingBy(Employee::getJob, Collectors.averagingDouble(Employee::getSalary))));

    for (Department dept : depts) {
      // Operation 부서는 직원이 존재하지 않기 때문에 get 실행 시, NPE가 발생한다.
      // Optional을 사용해서 Operation 도 결과가 나타나도록 처리했다.
      Optional.ofNullable(results.get(dept))
          .ifPresentOrElse((employeeJobDoubleMap) -> {
                for (EmployeeJob employeeJob : employeeJobDoubleMap.keySet()) {
                  int amount = (int) Math.round(employeeJobDoubleMap.get(employeeJob));
                  int amountSize = amount / 100;

                  System.out.printf("%10s | %10s : ", dept.getName(),
                      employeeJob);

                  for (int i = 0; i < amountSize; i++) {
                    System.out.print("*");
                  }

                  System.out.println();
                }
              },
              () -> {
                System.out.printf("%10s | %10s : ", dept.getName(),
                    "Null");
              });
    }
  }

  @Test
  public void crossJoin_Stream() {
    // 모든 부서와 Job의 급여평균을 보여준다.
    // 실행순서 : ((부서 cross join Job) left outer join 부서/Job 별 급여 평균)
    String sql;

    sql = "select e from Employee e";
    List<Employee> employees = entityManager.createQuery(sql, Employee.class)
        .getResultList();

    Map<String, Map<EmployeeJob, Double>> data = employees.stream()
        .collect(Collectors.groupingBy(x -> x.getDepartment().getName(),
            Collectors.groupingBy(Employee::getJob, Collectors.averagingDouble(Employee::getSalary))));

    sql = "select d from Department d";
    // 모든 부서와 Job 은 Cross Join으로 데이터를 가져온다.
    List<DeptJobCountDto> dtos = entityManager.createQuery(sql, Department.class)
        .getResultStream()
        .flatMap(x -> Arrays.stream(EmployeeJob.values())
            .map(t -> new DeptJobCountDto(x.getName(), t)))
        .map(x -> {
          String departmentName = x.getDepartmentName();
          EmployeeJob job = x.getJob();
          Double amount = Optional.ofNullable(data.get(departmentName))
              .map((value) -> value.getOrDefault(job, 0d))
              .orElse(0d);
          return new DeptJobCountDto(departmentName, job, amount);
        })
        .collect(Collectors.toList());

    for (DeptJobCountDto dto : dtos) {
      int amountSize = ((int) Math.round(dto.getAmount())) / 100;

      System.out.printf("%10s | %10s : ", dto.getDepartmentName(),
          dto.getJob());

      for (int i = 0; i < amountSize; i++) {
        System.out.print("*");
      }

      System.out.println();
    }
  }
}