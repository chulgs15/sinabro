package domain;

import java.util.List;

public class SalaryGrades {
  private final List<SalaryGrade> salaryGrades;

  public SalaryGrades(List<SalaryGrade> salaryGrades) {
    this.salaryGrades = salaryGrades;
  }

  public SalaryGrade getSalaryGrade(Long salary) {
    return salaryGrades.stream()
        .filter(x -> salary >= x.getLowSalary() &&
            salary <= x.getHighSalary())
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Salary 에러 발생."));
  }
}
