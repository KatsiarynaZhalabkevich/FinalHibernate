package by.epam.web.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
    private static final EntityManagerFactory factory;
    static {
        factory= Persistence.createEntityManagerFactory("by.epam.web");
    }
    public static EntityManager getEntityManager(){
        return factory.createEntityManager();
    }
    public static void close(){
        factory.close();
    }
}
