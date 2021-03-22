package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "salgrade")
public class SalaryGrade {
    @Id
    @Column(name = "grade")
    private Integer grade;

    @Column(name = "losal")
    private Long lowSalary;

    @Column(name = "hisal")
    private Long highSalary;

    public SalaryGrade() {
    }

    public SalaryGrade(Integer grade, Long lowSalary, Long highSalary) {
        this.grade = grade;
        this.lowSalary = lowSalary;
        this.highSalary = highSalary;
    }

    public Integer getGrade() {
        return grade;
    }

    public Long getLowSalary() {
        return lowSalary;
    }

    public Long getHighSalary() {
        return highSalary;
    }

    @Override
    public String toString() {
        return "SalaryGrade{" +
            "grade=" + grade +
            ", lowSalary=" + lowSalary +
            ", highSalary=" + highSalary +
            '}';
    }


}
