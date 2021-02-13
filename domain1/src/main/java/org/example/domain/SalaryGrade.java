package org.example.domain;

public class SalaryGrade {
    private Integer grade;
    private Long lowSalary;
    private Long highSalary;

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
