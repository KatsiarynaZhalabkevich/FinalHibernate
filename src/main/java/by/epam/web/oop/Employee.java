package by.epam.web.oop;

import by.epam.web.oop.JobPosition;
import by.epam.web.oop.Title;

import java.math.BigDecimal;
import java.util.Objects;

public class Employee {
    private String name;
    private Title title;
    private JobPosition jobPosition;
    private double workingHours;
    private boolean bonuses;
    private BigDecimal salary = BigDecimal.valueOf(100);

    public Employee(String name, Title title, JobPosition jobPosition) {
        this.name = name;
        this.title = title;
        this.jobPosition = jobPosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public JobPosition getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(JobPosition jobPosition) {
        this.jobPosition = jobPosition;
    }

    public double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(double workingHours) {
        this.workingHours = workingHours;
    }

    public boolean isBonuses() {
        return bonuses;
    }

    public void setBonuses(boolean bonuses) {
        this.bonuses = bonuses;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = this.salary.add(salary);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return Double.compare(employee.getWorkingHours(), getWorkingHours()) == 0 &&
                isBonuses() == employee.isBonuses() &&
                getName().equals(employee.getName()) &&
                getTitle() == employee.getTitle() &&
                getJobPosition() == employee.getJobPosition() &&
                getSalary().equals(employee.getSalary());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getTitle(), getJobPosition(), getWorkingHours(), isBonuses(), getSalary());
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", title=" + title +
                ", jobPosition=" + jobPosition +
                ", workingHours=" + workingHours +
                ", bonuses=" + bonuses +
                ", salary=" + salary +
                '}';
    }

}
