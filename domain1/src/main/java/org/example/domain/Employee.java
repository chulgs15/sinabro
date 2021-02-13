package org.example.domain;


import org.example.domain.enums.EmployeeJob;

import java.time.LocalDate;
import java.util.Optional;

public class Employee {
    private Long empNo;
    private String name;
    private EmployeeJob job;
    private Employee manager;
    private LocalDate hiredate;
    private Long salary;
    private Long commission;
    private Department department;

    public Employee(Long empNo, String name, EmployeeJob job,
                    LocalDate hiredate,
                    Long salary,
                    Long commission) {
        this.empNo = empNo;
        this.name = name;
        this.job = job;
        this.hiredate = hiredate;
        this.salary = salary;
        this.commission = commission;
    }

    public void setManager(Employee employee) {
        this.manager = employee;
    }

    void appointDepartment(Department department) {
        this.department = department;
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
            '}';
    }
}
