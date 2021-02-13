package org.example;

import org.example.domain.Department;
import org.example.domain.Employee;
import org.example.domain.SalaryGrade;
import org.example.domain.enums.EmployeeJob;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class Scott {
    public void main() {
        Department accounting = new Department(10L, "ACCOUNTING", "NEW YORK");
        Department research = new Department(20L, "RESEARCH", "DALLAS");
        Department sales = new Department(30L, "SALES", "CHICAGO");
        Department operations = new Department(40L, "OPERATIONS", "BOSTON");

        List<Department> departments = List.of(accounting, research, sales, operations);

        Random random = new Random();

        Employee smith = new Employee(7369L, "SMITH", EmployeeJob.CLERK, LocalDate.now().minusDays(random.nextInt(50)), 800L, null);
        Employee allen = new Employee(7499L, "ALLEN", EmployeeJob.SALESMAN, LocalDate.now().minusDays(random.nextInt(50)), 1600L, 300L);
        Employee ward = new Employee(7521L, "WARD", EmployeeJob.SALESMAN, LocalDate.now().minusDays(random.nextInt(50)), 1250L, 500L);
        Employee jones = new Employee(7566L, "JONES", EmployeeJob.MANAGER, LocalDate.now().minusDays(random.nextInt(50)), 2975L, null);
        Employee martin = new Employee(7654L, "MARTIN", EmployeeJob.SALESMAN, LocalDate.now().minusDays(random.nextInt(50)), 1250L, 1400L);
        Employee blake = new Employee(7698L, "BLAKE", EmployeeJob.MANAGER, LocalDate.now().minusDays(random.nextInt(50)), 2850L, null);
        Employee clark = new Employee(7782L, "CLARK", EmployeeJob.MANAGER, LocalDate.now().minusDays(random.nextInt(50)), 2450L, null);
        Employee scott = new Employee(7788L, "SCOTT", EmployeeJob.ANALYST, LocalDate.now().minusDays(random.nextInt(50)), 3000L, null);
        Employee king = new Employee(7839L, "KING", EmployeeJob.PRESIDENT, LocalDate.now().minusDays(random.nextInt(50)), 5000L, null);
        Employee turner = new Employee(7844L, "TURNER", EmployeeJob.SALESMAN, LocalDate.now().minusDays(random.nextInt(50)), 1500L, 0L);
        Employee adams = new Employee(7876L, "ADAMS", EmployeeJob.CLERK, LocalDate.now().minusDays(random.nextInt(50)), 1100L, null);
        Employee james = new Employee(7900L, "JAMES", EmployeeJob.CLERK, LocalDate.now().minusDays(random.nextInt(50)), 950L, null);
        Employee ford = new Employee(7902L, "FORD", EmployeeJob.ANALYST, LocalDate.now().minusDays(random.nextInt(50)), 3000L, null);
        Employee miller = new Employee(7934L, "MILLER", EmployeeJob.CLERK, LocalDate.now().minusDays(random.nextInt(50)), 1300L, null);

        // Manager-Member 연결 관계 결정.
        smith.setManager(ford);
        allen.setManager(blake);
        ward.setManager(blake);
        jones.setManager(king);
        martin.setManager(blake);
        blake.setManager(king);
        clark.setManager(king);
        scott.setManager(jones);
        turner.setManager(blake);
        adams.setManager(scott);
        james.setManager(blake);
        ford.setManager(jones);
        miller.setManager(clark);

        // 부서 발령.
        accounting.addEmployees(clark, king, miller);
        research.addEmployees(smith, jones, scott, adams, ford);
        sales.addEmployees(allen, ward, martin, blake, turner, james);

        List<Employee> employees = List.of(smith, allen, ward,
            jones, martin, blake, clark, scott, king, turner, adams, james, ford, miller);

        // SalaryGrade
        SalaryGrade firstGrade = new SalaryGrade(1, 700L, 1200L);
        SalaryGrade secondGrade = new SalaryGrade(2, 1201L, 1400L);
        SalaryGrade thirdGrade = new SalaryGrade(3, 1401L, 2000L);
        SalaryGrade fourthGrade = new SalaryGrade(4, 2001L, 3000L);
        SalaryGrade fifthGrade = new SalaryGrade(5, 3001L, 9999L);

        List<SalaryGrade> grades = List.of(firstGrade, secondGrade, thirdGrade
            , fourthGrade, fifthGrade);

        // 직원별 SalaryGrade 를 적용.
        employees.stream()
            .forEach(x -> x.setSalaryGrade(grades));

        for (Department department : departments) {
            System.out.println(department);
        }

        for (Employee employee : employees) {
            System.out.println(employee);
        }

        for (SalaryGrade grade : grades) {
            System.out.println(grade);
        }


    }
}
