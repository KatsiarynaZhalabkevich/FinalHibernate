package by.epam.web.loader;

import by.epam.web.bean.util.PasswordCreater;

import java.math.BigDecimal;

public class PasswordRunner {
    public static void main(String[] args) {
        String user = "user";
        String petr = "petr";
        String ivan = "ivan";

        System.out.println(user+"   "+ PasswordCreater.createPassword(user));
        System.out.println(petr+"   "+ PasswordCreater.createPassword(petr));
        System.out.println(ivan+"   "+ PasswordCreater.createPassword(ivan));


        BigDecimal count = BigDecimal.valueOf(5);

        BigDecimal sum = count;

        count.add(sum);


        System.out.println(count+"     "+sum);

    }
}
