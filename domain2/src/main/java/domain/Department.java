package domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dept")
public class Department {

    @Id
    @Column(name = "deptno")
    private Long deptNo;

    @Column(name = "dname")
    private String name;

    @Column(name = "loc")
    private String location;

    @OneToMany(mappedBy = "department", cascade = CascadeType.PERSIST)
    private List<Employee> employees = new ArrayList<>();

    public Department() {
    }

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
