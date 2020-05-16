package by.epam.web.oop.view;

import by.epam.web.oop.Employee;

import java.util.List;

public interface EmployeeView {
    void showEmployees(List<Employee> employees);
    List<Employee> createEmployees();
}
