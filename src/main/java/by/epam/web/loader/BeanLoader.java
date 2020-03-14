package by.epam.web.loader;

import by.epam.web.bean.Tarif;
import by.epam.web.bean.User;
import by.epam.web.util.HibernateUtil;

import javax.persistence.EntityManager;


public class BeanLoader {
    public static void main(String[] args) throws Exception {
        User user = new User("Katsiaryna", "Zhalabkevich","user","user" );
        EntityManager entityManager= HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        HibernateUtil.close();

       /* Tarif tarif = new Tarif("Home 45", 25, 200, 0,"cool tariff");
        entityManager= HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(tarif);
        entityManager.getTransaction().commit();
        HibernateUtil.close();*/
    }
}
