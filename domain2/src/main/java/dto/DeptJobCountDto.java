package dto;

import domain.enums.EmployeeJob;

public class DeptJobCountDto {
  private String departmentName;
  private EmployeeJob job;
  private Double amount;

  public DeptJobCountDto() {
  }

  public DeptJobCountDto(String departmentName, EmployeeJob job, Double amount) {
    this.departmentName = departmentName;
    this.job = job;
    this.amount = amount;
  }

  public DeptJobCountDto(String departmentName, EmployeeJob job) {
    this.departmentName = departmentName;
    this.job = job;
    this.amount = 0d;
  }

  public String getDepartmentName() {
    return departmentName;
  }

  public EmployeeJob getJob() {
    return job;
  }

  public Double getAmount() {
    return amount;
  }

  @Override
  public String toString() {
    return "DeptJobCount{" +
        "departmentName='" + departmentName + '\'' +
        ", job=" + job +
        ", amount=" + amount +
        '}';
  }
}
