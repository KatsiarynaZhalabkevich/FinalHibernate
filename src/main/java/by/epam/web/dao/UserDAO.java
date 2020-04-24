package by.epam.web.dao;

import by.epam.web.bean.User;

import java.util.List;

public interface UserDAO {

    User findUserByLogin(String login) throws DAOException;

    boolean addUser(User user) throws DAOException;

    User findUserById(int id) throws DAOException;

    boolean isLoginExist(String login) throws DAOException;

    List<User> takeAllUser() throws DAOException;

    boolean updateUserInfo(User user) throws DAOException;

    boolean deleteUser(int id) throws DAOException;

    String getPasswordById(int id) throws DAOException;

    boolean updatePassword(User user) throws DAOException;

    boolean updateIsActive(boolean active, int id) throws DAOException;

    boolean updateUserBalanceById(int id, double balance) throws DAOException;

    List<User> getUsersRange(int page, int limit) throws DAOException;

    List<User> findUsersByName(String name) throws DAOException;

    List<User> findUsersBySurname(String surname) throws DAOException;

    List<User> findUsersByEmail(String email) throws DAOException;

    List<User> findUsersByPhone(String phone) throws DAOException;


}
