package by.epam.web.oop;

import by.epam.web.oop.Employee;
import by.epam.web.oop.JobPosition;
import by.epam.web.oop.Title;
import by.epam.web.oop.CalculationLogic;
import by.epam.web.oop.view.EmployeeView;
import by.epam.web.oop.view.impl.ConsoleEmployeeView;
import by.epam.web.oop.view.impl.FileEmployeesView;

import java.math.BigDecimal;
import java.util.List;

public class Main {

    public static void main(String[] args) {

       //вывод в файл
        EmployeeView employeesView = new FileEmployeesView();
        List<Employee> lst = employeesView.createEmployees();
        //employeesView.showEmployees(lst);

        EmployeeView employeesView2 = new ConsoleEmployeeView();
        List<Employee> lst2 = employeesView2.createEmployees();
        employeesView2.showEmployees(lst2);



//        CalculationLogic calculationLogic = new CalculationLogic();
//        Employee employee1 = new Employee("Ivan", Title.JUNIOR, JobPosition.TESTER);
//        employee1.setBonuses(true);
//        employee1.setWorkingHours(10);
//
//        employee1.setSalary(BigDecimal.valueOf(220));
//        System.out.println(employee1.getSalary());
//
//        Employee employee2 = new Employee("Petr", Title.MIDDLE, JobPosition.KEY_TESTER);
//        employee2.setWorkingHours(10);
//        Employee employee3 = new Employee("Fedor", Title.SENIOR, JobPosition.TESTING_TEAM_LEAD);
//        employee3.setBonuses(true);
//        employee3.setWorkingHours(10);
//
//        System.out.println(employee1+" total salary is "+calculationLogic.calculateSalary(employee1));
//        System.out.println(employee2+" total salary is "+calculationLogic.calculateSalary(employee2));
//        System.out.println(employee3+" total salary is "+calculationLogic.calculateSalary(employee3));
//        BigDecimal earnedSalary = calculationLogic.calculateSalary(employee1);
//        System.out.println(earnedSalary);

    }

}
