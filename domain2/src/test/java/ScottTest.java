import domain.Employee;
import domain.SalaryGrade;
import domain.SalaryGrades;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

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

    scott.changeSalary(5000L);
    System.out.printf("%s's salary grade is %d", scott.getName(),
        scott.getSalaryGrade().getGrade());

    Assertions.assertEquals((int) scott.getSalaryGrade().getGrade(), 5);
  }

  @Test
  public void getEmployeeUpstreamHierarchy() {
    Employee starter = entityManager.find(Employee.class, 7839L);
    System.out.println(starter);
    printMember(starter, 0);
  }

  private void printMember(Employee manager, int level) {
    // manager가 member 를 가지고 있는지 조회한다.
    // member가 존재하면, 다시 printMember 를 재귀호출해서 데이터가 나타날때 까지 조회한다.
    // member가 존재하지 않으면 재귀호출을 멈춘다.
    List<Employee> members = manager.getMembers();

    if (members.size() != 0) {
      level++;
      for (Employee member : members) {
        System.out.printf("%" + (level + member.toString().length() + 3) + "s \n",
            " └▶ " + member.toString());
        printMember(member, level);
      }
    }
  }

  @Test
  public void getEmployeeDownStreamHierarchy() {
    Employee starter = entityManager.find(Employee.class, 7876L);

    // 주의!! 재귀 호출 method 실행 부분
    System.out.println(starter);
    printManager(starter, 0);
  }

  private void printManager(Employee member, int level) {
    // Manager 는 King 을 제외한 모든 직원이 1명은 지정했다.
    // @ManyToOne 연관관계를 이용해서 승인권자를 그래프 탐색한다.
    if (member.getManager() != null) {
      level++;
      System.out.printf("%" + (level + member.getManager().toString().length() + 3) + "s \n",
          " └▶ " + member.getManager().toString());

      printManager(member.getManager(), level);
    }
  }
}
