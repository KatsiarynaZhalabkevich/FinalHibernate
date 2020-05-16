package by.epam.web.oop;

import by.epam.web.oop.Employee;
import by.epam.web.oop.JobPosition;
import by.epam.web.oop.Title;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CalculationLogic {

    public void paySalaryPerMonth(List<Employee>employees){
        BigDecimal salary;
        for (Employee e:employees){
          //  e.setSalary(calculateSalary(e));
            salary=calculateSalary(e);
            e.setSalary(salary);
        }
    }


    public BigDecimal calculateSalary(Employee employee) {
        BigDecimal costPerHour = Coefficients.COST_PER_HOUR;
        BigDecimal result1 = costPerHour.multiply(BigDecimal.valueOf(employee.getWorkingHours()));
        BigDecimal result3 = calculateJobPosition(result1, employee);
        BigDecimal result4 = BigDecimal.valueOf(0);
        BigDecimal salary;


        if (employee.isBonuses()) {
            result4 = result3;
        }
        salary = result1.add(calculateTitle(result1, employee)).add(calculateJobPosition(result1, employee)).add(result4);
        return salary;
    }

    private BigDecimal calculateTitle(BigDecimal result1, Employee employee) {
        BigDecimal result2;
        if (Title.JUNIOR.equals(employee.getTitle())) {
            result2 = result1.multiply(Coefficients.JUNIOR_COEFFICIENT);
        } else if (Title.MIDDLE.equals(employee.getTitle())) {
            result2 = result1.multiply(Coefficients.MIDDLE_COEFFICIENT);
        } else result2 = result1.multiply(Coefficients.SENIOR_COEFFICIENT);
        return result2;
    }

    private BigDecimal calculateJobPosition(BigDecimal result1, Employee employee) {
        BigDecimal result3;
        if (JobPosition.TESTER.equals(employee.getJobPosition())) {
            result3 = result1.multiply(Coefficients.TESTER_COEFFICIENT);
        } else if (JobPosition.KEY_TESTER.equals(employee.getJobPosition())) {
            result3 = result1.multiply(Coefficients.KEY_TESTER_COEFFICIENT);
        } else {
            result3 = result1.multiply(Coefficients.TESTING_TEAM_LEAD_COEFFICIENT);
        }
        return result3;

    }
}

