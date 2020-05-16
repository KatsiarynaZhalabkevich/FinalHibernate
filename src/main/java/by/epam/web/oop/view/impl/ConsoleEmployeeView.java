package by.epam.web.oop.view.impl;

import by.epam.web.oop.Employee;
import by.epam.web.oop.JobPosition;
import by.epam.web.oop.Title;
import by.epam.web.oop.view.EmployeeView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ConsoleEmployeeView implements EmployeeView {
    @Override
    public void showEmployees(List<Employee> employees) {
        System.out.println("___________________________");
        System.out.println("List of Employees with salary for a month:");

        for(Employee e:employees){
            System.out.println();
            System.out.println("Name: "+e.getName());
            System.out.println("Title: "+e.getTitle());
            System.out.println("Job position: "+e.getJobPosition());
            System.out.println("Working hours (this month): "+e.getWorkingHours());
            if(e.isBonuses()){
                System.out.println("Bonuses (yes/no): Yes");
            }else  System.out.println("Bonuses (yes/no): No");
//добавить поле выплата и текущий баланс????
            System.out.println("Salary : "+e.getSalary());
            System.out.println("-------------------------------");
        }

    }

    @Override
    public List<Employee> createEmployees() {
        List<Employee> employees = new ArrayList<>();

        Employee employee1 = new Employee("Ivan", Title.JUNIOR, JobPosition.TESTER);
        employee1.setBonuses(true);
        employee1.setWorkingHours(10);

        Employee employee2 = new Employee("Petr", Title.MIDDLE, JobPosition.KEY_TESTER);
        employee2.setWorkingHours(10);

        Employee employee3 = new Employee("Fedor", Title.SENIOR, JobPosition.TESTING_TEAM_LEAD);
        employee3.setBonuses(true);
        employee3.setWorkingHours(10);

        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);

        return employees;
    }
}
