package by.epam.web.loader;

import by.epam.web.bean.Tarif;
import by.epam.web.bean.User;
import by.epam.web.util.HibernateUtil;

import javax.persistence.EntityManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class BeanLoader {
    public static void main(String[] args) throws Exception {

        User user = new User("Katsiaryna", "Zhalabkevich","+375296902620","beluzhka@gmail.com", "user", "user");
/*
        String url = "jdbc:mysql://localhost:3306/hiber_telecom?serverTimezone=Europe/Minsk&useSSL=false";
        String username = "root";
        String password = "123456";
        System.out.println("Connecting...");



        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO user (age, name, surname ) values (?,?,?);");
            statement.setInt(1, user.getAge());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSurname());
            if (statement.executeUpdate() == 1) {
                System.out.println("INSERT OK");
            }
            System.out.println("Connection successful!");
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }

 */
        EntityManager entityManager= HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
       // HibernateUtil.close();

       Tarif tarif = new Tarif("Home 45", 25, 200, 0,"cool tariff");
        entityManager= HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(tarif);
        entityManager.getTransaction().commit();
        HibernateUtil.close();

    }
}
