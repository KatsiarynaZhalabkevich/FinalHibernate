package by.epam.web.dao.impl;

import by.epam.web.bean.User;
import by.epam.web.dao.BaseDao;
import by.epam.web.dao.DAOException;
import by.epam.web.dao.Dao;
import by.epam.web.dao.UserDAO;
import by.epam.web.util.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * HQL
 */
@Repository
public class HibernateUserDAO  implements UserDAO {

    private final static Logger logger = LogManager.getLogger();
    @PersistenceContext
    private EntityManager em;


    @Override
    public User findUserByLogin(String login) throws DAOException {

        try {
            return (User) em.createQuery("from User where login = :login")
                    .setParameter("login", login)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (JDBCException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean addUser(User user) throws DAOException {
        boolean flag ;
        logger.info("попали в метод эдд юзер");
        try {
            logger.info("все ок. готовимся к транзакции");
            em.persist(user);
            logger.info("транзакция прошла. юзер создан");
            flag = true;
        } catch (HibernateException e) {
            logger.error("что-то пошло не так. юзер не создан");
            throw new DAOException(e);
        }
        return flag;

    }

    @Override
    public User findUserById(int id) throws DAOException {
        try {
            return em.find(User.class, id);
        } catch (HibernateException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public boolean isLoginExist(String login) throws DAOException {
        try {
            List<User> users = em.createQuery("from User where login= :login")
                    .setParameter("login", login)
                    .getResultList();
            return users.size() > 0;
        } catch (HibernateException e) {
            throw new DAOException(e);
        }


    }

    @Override
    public List<User> takeAllUser() throws DAOException {
        try {
            return (List<User>) em.createQuery("from User").getResultList();
        } catch (HibernateException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public boolean updateUserInfo(User user) throws DAOException {
        try {
            int count = em.createQuery("update User set name = :name, " +
                    "surname = :surname, " +
                    "phone = :phone, " +
                    "email = :email " +
                    "where id = :id ")
                    .setParameter("name", user.getName())
                    .setParameter("surname", user.getSurname())
                    .setParameter("phone", user.getPhone())
                    .setParameter("email", user.getEmail())
                    .setParameter("id", user.getId())
                    .executeUpdate();
            System.out.println("сколько строчек преобразовалось " + count);
            return count == 1;
        } catch (RollbackException e) {
            em.getTransaction().rollback();
            em.close();
            throw new DAOException(e);
        }

    }

    @Override
    public boolean deleteUser(int id) throws DAOException {

        try {
            User user = em.find(User.class, id);
            em.remove(user);
            return user != null;
        } catch (RollbackException e) {
            throw new DAOException(e);
        }


    }

    @Override
    public String getPasswordById(int id) throws DAOException {
        User user = findUserById(id);
        return user.getPassword();
    }

    @Override
    public boolean updatePassword(User user) throws DAOException {

        try {
            int count = em.createQuery("update User set password = :password where id = :id")
                    .setParameter("password", user.getPassword())
                    .setParameter("id", user.getId())
                    .executeUpdate();
            return count == 1;
        } catch (RollbackException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public boolean updateIsActive(boolean active, int id) throws DAOException {
        try {

            int count = em.createQuery("update User set active = :active where id = :id")
                    .setParameter("active", active)
                    .setParameter("id", id)
                    .executeUpdate();
            return count == 1;
        } catch (RollbackException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public boolean updateUserBalanceById(int id, double balance) throws DAOException {
        try {

            int count = em.createQuery("update User set balance = :balance where id = :id")
                    .setParameter("balance", balance)
                    .setParameter("id", id)
                    .executeUpdate();
            return count == 1;
        } catch (RollbackException e) {

            throw new DAOException(e);
        }

    }

    @Override
    public List<User> getUsersRange(int page, int limit) throws DAOException {
               try {
            Query query =em.createQuery("from User ");

            return (List<User>) query.setMaxResults(limit)
                    .setFirstResult(page * limit)
                    .getResultList();
        } catch (HibernateException e) {

            throw new DAOException(e);
        }


    }

    @Override
    public List<User> findUsersByName(String name) throws DAOException {

        try {
           em.getTransaction().begin();
            Query query = em.createQuery("from User where name = :name")
                    .setParameter("name", name + "%");

            return (List<User>) query.getResultList();
        } catch (RollbackException e) {

            throw new DAOException(e);
        }

    }

    @Override
    public List<User> findUsersBySurname(String surname) throws DAOException {

        try {
            Query query = em.createQuery("from User where surname = :surname")
                    .setParameter("surname", surname + "%");

            return (List<User>) query.getResultList();
        } catch (HibernateException e) {

            throw new DAOException(e);
        }

    }

    @Override
    public List<User> findUsersByEmail(String email) throws DAOException {

        try {
            Query query = em.createQuery("from User where email = :email")
                    .setParameter("email", email + "%");


            return (List<User>) query.getResultList();

        } catch (HibernateException e) {

            throw new DAOException(e);
        }

    }


    @Override
    public List<User> findUsersByPhone(String phone) throws DAOException {

        try {
            Query query = em.createQuery("from User where phone = :phone")
                    .setParameter("phone", phone + "%");

            return (List<User>) query.getResultList();
        } catch (HibernateException e) {

            throw new DAOException(e);
        }

    }
}
