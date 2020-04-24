package by.epam.web.dao.impl;

import by.epam.web.bean.User;
import by.epam.web.dao.DAOException;
import by.epam.web.dao.UserDAO;
import by.epam.web.util.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * HQL
 */
public class HibernateUserDAO implements UserDAO {

    private final static Logger logger = LogManager.getLogger();

    @Override
    public User findUserByLogin(String login) throws DAOException {
        Session session = HibernateUtil.getSession();
        try {
            return (User) session.createQuery("from User where login = :login")
                    .setParameter("login", login)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (JDBCException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean addUser(User user) throws DAOException {
        boolean flag = false;
        logger.info("попали в метод эдд юзер");
        Session session = HibernateUtil.getSession();
        try {
            logger.info("все ок. готовимся к транзакции");
            session.getTransaction().begin();
            session.persist(user);
            session.getTransaction().commit();
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
        Session session = HibernateUtil.getSession();
        try {
            return session.find(User.class, id);
        } catch (HibernateException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public boolean isLoginExist(String login) throws DAOException {
        Session session = HibernateUtil.getSession();

        try {
            List<User> users = session.createQuery("from User where login= :login")
                    .setParameter("login", login)
                    .getResultList();
            return users.size() > 0;
        } catch (HibernateException e) {
            throw new DAOException(e);
        }


    }

    @Override
    public List<User> takeAllUser() throws DAOException {
        Session session = HibernateUtil.getSession();
        try {
            return (List<User>) session.createQuery("from User").getResultList();
        } catch (HibernateException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public boolean updateUserInfo(User user) throws DAOException {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            int count = session.createQuery("update User set name = :name, " +
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
            session.getTransaction().commit();
            session.close();
            System.out.println("сколько строчек преобразовалось " + count);
            return count == 1;
        } catch (RollbackException e) {
            session.getTransaction().rollback();
            session.close();
            throw new DAOException(e);
        }

    }

    @Override
    public boolean deleteUser(int id) throws DAOException {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            User user = session.find(User.class, id);
            session.remove(user);
            session.getTransaction().commit();
            session.close();
            return user != null;
        } catch (RollbackException e) {
            session.getTransaction().rollback();
            session.close();
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
        Session session = HibernateUtil.getSession();
        try {
            session.getTransaction().begin();
            int count = session.createQuery("update User set password = :password where id = :id")
                    .setParameter("password", user.getPassword())
                    .setParameter("id", user.getId())
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
            return count == 1;
        } catch (RollbackException e) {
            session.getTransaction().rollback();
            session.close();
            throw new DAOException(e);
        }

    }

    @Override
    public boolean updateIsActive(boolean active, int id) throws DAOException {
        Session session = HibernateUtil.getSession();
        try {
            session.getTransaction().begin();
            int count = session.createQuery("update User set active = :active where id = :id")
                    .setParameter("active", active)
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
            return count == 1;
        } catch (RollbackException e) {
            session.getTransaction().rollback();
            session.close();
            throw new DAOException(e);
        }

    }

    @Override
    public boolean updateUserBalanceById(int id, double balance) throws DAOException {
        Session session = HibernateUtil.getSession();

        try {
            session.beginTransaction();
            int count = session.createQuery("update User set balance = :balance where id = :id")
                    .setParameter("balance", balance)
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
            return count == 1;
        } catch (RollbackException e) {
            session.getTransaction().rollback();
            session.close();
            throw new DAOException(e);
        }

    }

    @Override
    public List<User> getUsersRange(int page, int limit) throws DAOException {
        Session session = HibernateUtil.getSession();

        try {
            Query query = session.createQuery("from User ");
            session.close();

            return (List<User>) query.setMaxResults(limit)
                    .setFirstResult(page * limit)
                    .getResultList();
        } catch (HibernateException e) {
            session.close();
            throw new DAOException(e);
        }


    }

    @Override
    public List<User> findUsersByName(String name) throws DAOException {

        Session session = HibernateUtil.getSession();
        try {
            session.getTransaction().begin();
            Query query = session.createQuery("from User where name = :name")
                    .setParameter("name", name + "%");
            session.close();
            return (List<User>) query.getResultList();
        } catch (RollbackException e) {
            session.getTransaction().rollback();
            session.close();
            throw new DAOException(e);
        }

    }

    @Override
    public List<User> findUsersBySurname(String surname) throws DAOException {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from User where surname = :surname")
                    .setParameter("surname", surname + "%");
            session.close();
            return (List<User>) query.getResultList();
        } catch (HibernateException e) {
            session.close();
            throw new DAOException(e);
        }

    }

    @Override
    public List<User> findUsersByEmail(String email) throws DAOException {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from User where email = :email")
                    .setParameter("email", email + "%");
            session.close();

            return (List<User>) query.getResultList();

        } catch (HibernateException e) {
            session.close();
            throw new DAOException(e);
        }

    }


    @Override
    public List<User> findUsersByPhone(String phone) throws DAOException {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from User where phone = :phone")
                    .setParameter("phone", phone + "%");
            session.close();
            return (List<User>) query.getResultList();
        } catch (HibernateException e) {
            session.close();
            throw new DAOException(e);
        }

    }
}
