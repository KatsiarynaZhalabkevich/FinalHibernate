package by.epam.web.dao.impl;

import by.epam.web.bean.User;
import by.epam.web.dao.DAOException;
import by.epam.web.dao.UserDAO;
import by.epam.web.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 *  HQL
 */
public class HibernateUserDAO implements UserDAO {
    @Override //сейчас невозможно проверить, тк записи не уникальны
    //убрать 1 результат
    public User findUserByLogin(String login) throws DAOException {
        Session session = HibernateUtil.getSession();
        return (User) session.createQuery("from User where login = :login")
                .setParameter("login", login)
                .setMaxResults(1)
                .getSingleResult();
    }

    @Override
    public boolean addUser(User user) throws DAOException {
        boolean flag = false;
        Session session = HibernateUtil.getSession();
        try {
            session.getTransaction().begin();
            session.persist(user);
            session.getTransaction().commit();
            flag = true;
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return flag;

    }

    @Override
    public User findUserById(int id) throws DAOException {
        Session session = HibernateUtil.getSession();
        return session.find(User.class, id);
    }

    @Override
    public boolean isLoginExist(String login) throws DAOException {
        Session session = HibernateUtil.getSession();
        User user = session.find(User.class, login);
        return user != null;

    }

    @Override
    public List<User> takeAllUser() throws DAOException {
        Session session = HibernateUtil.getSession();
        return (List<User>) session.createQuery("from User").getResultList();
    }

    @Override
    public boolean updateUserInfo(User user) throws DAOException {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        int count = session.createQuery("update User set name = :name, " +
                "surname = :surname, " +
                "phone = :phone, " +
                "email = :email " +
                "where id = :id")
                .setParameter("name", user.getName())
                .setParameter("surname", user.getSurname())
                .setParameter("phone", user.getPhone())
                .setParameter("email", user.getEmail())
                .setParameter("id", user.getId())
                .executeUpdate();
        session.getTransaction().commit();
        return count == 1;
    }

    @Override
    public boolean deleteUser(int id) throws DAOException {

        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        User user = session.find(User.class, id);
        session.remove(user);
        session.getTransaction().commit();

        return user != null;

    }

    @Override //может сделать свою логику, чтобы было удобно читать
    public String getPasswordById(int id) throws DAOException {
        User user = findUserById(id);
        return user.getPassword();
    }

    @Override
    public boolean updatePassword(User user) throws DAOException {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        int count = session.createQuery("update User set password = :password where id = :id")
                .setParameter("password", user.getPassword())
                .setParameter("id", user.getId())
                .executeUpdate();
        session.getTransaction().commit();
        return count == 1;
    }

    @Override
    public boolean updateIsActive(boolean active, int id) throws DAOException {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        int count = session.createQuery("update User set active = :active where id = :id")
                .setParameter("active", active)
                .setParameter("id", id)
                .executeUpdate();
        session.getTransaction().commit();

        return count == 1;
    }

    @Override
    public boolean updateUserBalanceById(int id, double balance) throws DAOException {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        int count = session.createQuery("update User set balance = :balance where id = :id")
                .setParameter("balance", balance)
                .setParameter("id", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return count == 1;
    }

    @Override
    public List<User> getUsersRange(int firstPosition, int limit) throws DAOException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("from User ");
        return query.setMaxResults(limit)
                .setFirstResult(firstPosition * limit)
                .getResultList();

    }

    @Override
    public List<User> findUsersByName(String name) throws DAOException {

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("from User where name = :name")
                .setParameter("name", name + "%");
        session.close();
        List<User> users = query.getResultList();
        return users;
    }

    @Override
    public List<User> findUsersBySurname(String surname) throws DAOException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("from User where surname = :surname")
                .setParameter("surname", surname + "%");
        List<User> users = query.getResultList();
        return users;
    }

    @Override
    public List<User> findUsersByEmail(String email) throws DAOException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("from User where email = :email")
                .setParameter("email", email + "%");
        List<User> users = query.getResultList();
        return users;
    }

    @Override
    public List<User> findUsersByPhone(String phone) throws DAOException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("from User where phone = :phone")
                .setParameter("phone", phone + "%");
        List<User> users = query.getResultList();
        return users;
    }
}
