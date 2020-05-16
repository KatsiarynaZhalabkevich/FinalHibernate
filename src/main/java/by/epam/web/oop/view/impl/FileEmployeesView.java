package by.epam.web.oop.view.impl;

import by.epam.web.oop.Employee;
import by.epam.web.oop.view.EmployeeView;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileEmployeesView implements EmployeeView {

    private final static String DATA_RESOURCE = "src\\main\\java\\by\\epam\\web\\oop\\view\\impl\\EmployeeData.txt";
    private final static String RESULT_RESOURCE = "src\\main\\java\\by\\epam\\web\\oop\\view\\impl\\employeeResult.txt";

    @Override
    public void showEmployees(List<Employee> employees) {
       String employToWrite;
       for (Employee e:employees){
           employToWrite = e.getName();
           employToWrite = employToWrite.concat(" "+e.getTitle());
           employToWrite = employToWrite.concat(" "+e.getJobPosition());
           employToWrite = employToWrite.concat(" "+e.getWorkingHours());
           employToWrite = employToWrite.concat(" "+e.isBonuses());
           employToWrite = employToWrite.concat(" "+e.getSalary());

           try {
               Files.write(Paths.get(RESULT_RESOURCE), employToWrite.getBytes(), StandardOpenOption.APPEND);
           } catch (IOException ex) {
               System.out.println(ex);
               System.out.println("Что-то пошло не так");
           }
       }


    }

    @Override
    public List<Employee> createEmployees() {
        List<String> lines= new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(DATA_RESOURCE));
            System.out.println(lines);
        } catch (IOException e) {
            System.out.println("Xnj-то пошло не так");
        }

        //Нужен парсер отдельно для строки


        return null;
    }
}
