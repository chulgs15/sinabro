package org.example.domain;


import java.util.ArrayList;
import java.util.List;

public class Department {
    private Long deptNo;
    private String name;
    private String location;
    private List<Employee> employees = new ArrayList<>();

    public Department(Long deptNumber, String deptName, String location) {
        this.deptNo = deptNumber;
        this.name = deptName;
           this.location = location;
    }

    public void addEmployees(Employee... employees) {
        for (Employee employee : employees) {
            this.employees.add(employee);
            employee.appointDepartment(this);
        }
    }

    public Long getDeptNo() {
        return deptNo;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String printEmployeeList() {
        return employees.toString();
    }

    @Override
    public String toString() {
        return "Department{" +
            "deptNo=" + deptNo +
            ", name='" + name + '\'' +
            ", location='" + location + '\'' +
            ", employees.size()=" + employees.size() +
            '}';
    }
}
