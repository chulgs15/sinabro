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
  private Long salary;

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
                  LocalDate hiredate, Long salary, Long commission) {
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

  public Long getSalary() {
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

  public void changeSalary(Long salary) {
    this.salary = salary;
  }

  void appointDepartment(Department department) {
    this.department = department;
  }
}
