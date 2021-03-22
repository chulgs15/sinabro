package domain;


import domain.enums.EmployeeJob;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "emp")
public class Employee {

  @Id
  @Column(name = "empno")
  private Long empNo;

  @Column(name = "ename")
  private String name;

  @Column(name = "job")
  @Enumerated(EnumType.STRING)
  private EmployeeJob job;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mgr")
  private Employee manager;

  @OneToMany(mappedBy = "manager")
  private List<Employee> members = new ArrayList<>();

  @Column(name = "hiredate")
  private LocalDate hiredate;

  @Column(name = "sal")
  private Double salary;

  @Column(name = "comm")
  private Long commission;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "deptno")
  private Department department;

  @Transient
  private SalaryGrades salaryGrades;

  public Employee() {
  }

  public Employee(Long empNo, String name, EmployeeJob job,
                  LocalDate hiredate, Double salary, Long commission) {
    this.empNo = empNo;
    this.name = name;
    this.job = job;
    this.hiredate = hiredate;
    this.salary = salary;
    this.commission = commission;
  }

  public Long getEmpNo() {
    return empNo;
  }

  public String getName() {
    return name;
  }

  public EmployeeJob getJob() {
    return job;
  }

  public Employee getManager() {
    return manager;
  }

  public LocalDate getHiredate() {
    return hiredate;
  }

  public Double getSalary() {
    return salary;
  }

  public Long getCommission() {
    return commission;
  }

  public Department getDepartment() {
    return department;
  }

  public List<Employee> getMembers() {
    return members;
  }

  public SalaryGrade getSalaryGrade() {
    return Optional.ofNullable(salaryGrades)
        .map(x -> x.getSalaryGrade(salary))
        .orElse(new SalaryGrade());
  }

  @Override
  public String toString() {
    return "Employee{" +
        "empNo=" + empNo +
        ", name='" + name + '\'' +
        ", job=" + job +
        ", manager=" + Optional.ofNullable(manager).map(Employee::getName).orElse("null") +
        ", hiredate=" + hiredate +
        ", salary=" + salary +
        ", commission=" + commission +
        ", department=" + department.getName() +
//        ", salaryGrade=" + Optional.ofNullable(getSalaryGrade()).map(SalaryGrade::getGrade).orElse(null)  +
        ", salaryGrade=" + getSalaryGrade().getGrade()  +
        '}';
  }

  public void changeManager(Employee employee) {
    this.manager = employee;
  }

  public void setGrade(SalaryGrades salaryGrades) {
    this.salaryGrades = salaryGrades;
  }

  public void changeSalary(Double salary) {
    this.salary = salary;
  }

  void appointDepartment(Department department) {
    this.department = department;
  }

  public void printMember(Employee manager, int level) {
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

  public void printManager(Employee member, int level) {
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
