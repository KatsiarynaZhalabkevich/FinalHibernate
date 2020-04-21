package by.epam.web.loader;

import by.epam.web.bean.Tarif;
import by.epam.web.bean.User;
import by.epam.web.dao.DAOProvider;
import by.epam.web.dao.TarifDAO;
import by.epam.web.dto.UserTarif;
import by.epam.web.service.ServiceProvider;
import by.epam.web.service.TarifService;
import by.epam.web.service.UserService;
import by.epam.web.util.HibernateUtil;

import javax.persistence.EntityManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class BeanLoader {
    public static void main(String[] args) throws Exception {

//        User user = new User("Katsiaryna", "Zhalabkevich", "+375296902620", "beluzhka@gmail.com", "login", "user");
//
//        UserService userService = ServiceProvider.getInstance().getUserService();
//        user.setSurname("Beliuzhenka");
//        if (userService.saveUpdateUser(user)) {
//            System.out.println("Все ок");
//        } else {
//            System.out.println("не все ок");
//        }
        TarifService tarifService = ServiceProvider.getInstance().getTarifService();
        TarifDAO tarifDAO = DAOProvider.getInstance().getTarifDAO();

        List<UserTarif> tariffs = tarifDAO.getTarifByUserId(13);

        System.out.println("получили лист");
        System.out.println("size = "+ tariffs.size());
        for(UserTarif t: tariffs){
            System.out.println(t);
        }


//        User user = new User("Katsiaryna", "Zhalabkevich", "+375296902620", "beluzhka@gmail.com", "login", "user");
//        EntityManager entityManager= HibernateUtil.getEntityManager();
//        entityManager.getTransaction().begin();
//        entityManager.persist(user);
//        entityManager.getTransaction().commit();


    }
}
