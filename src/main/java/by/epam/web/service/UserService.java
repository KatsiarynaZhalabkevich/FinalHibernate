package by.epam.web.service;


import by.epam.web.bean.User;

import java.util.List;

public interface UserService {
    User authorization(String login, String password) throws ServiceException;
    boolean saveUpdateUser(User user) throws ServiceException;
    boolean isLoginUniq(String login) throws ServiceException;
    List<User> getUsers() throws ServiceException;
    boolean deleteUser(int id) throws ServiceException;
    boolean changeBalanceById(int id, double balance) throws ServiceException;
    boolean changeStatusById(int id, boolean active) throws ServiceException;
    User getUserById(int id)throws ServiceException;
    List<User> getUsersRange(long page, int limit) throws ServiceException;
}
