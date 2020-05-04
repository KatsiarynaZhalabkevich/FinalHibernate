package by.epam.web.dao.impl;

import by.epam.web.bean.User;
import by.epam.web.dao.DAOException;
import by.epam.web.dao.UserDAO;
import by.epam.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SpringDataUserDAO implements UserDAO {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserByLogin(String login) throws DAOException {

        return userRepository.findUserByLogin(login);
    }

    @Override
    public boolean addUser(User user) throws DAOException {
        User userFromBD = userRepository.save(user);
        return userFromBD != null;
    }

    @Override
    public User findUserById(int id) throws DAOException {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.isPresent() ? optionalUser.get() : new User();
        return user;

    }

    @Override
    public boolean isLoginExist(String login) throws DAOException {
        return userRepository.getUserByLogin(login)!=null;
    }

    @Override
    public List<User> takeAllUser() throws DAOException {
        List<User> users = new ArrayList<>();
        for (User u : userRepository.findAll()) {
            users.add(u);
        }
        return users;
    }

    @Override
    public boolean updateUserInfo(User user) throws DAOException {
        User updatedUser = userRepository.saveAndFlush(user);
        return user.equals(updatedUser);
    }

    @Override
    public boolean deleteUser(int id) throws DAOException {
        userRepository.deleteById(id);
        return !userRepository.existsById(id);
    }

    @Override
    public String getPasswordById(int id) throws DAOException {
        User user = userRepository.getUserById(id);
        return user.getPassword();
    }

    @Override
    public boolean updatePassword(User user) throws DAOException {
        User updatedUser = userRepository.updatePassword(user.getPassword(), user.getId());
        return updatedUser != null;
    }

    @Override
    public boolean updateIsActive(boolean active, int id) throws DAOException {
        User updatedUser = userRepository.updateIsActive(active, id);
        return active==updatedUser.isActive();

    }

    @Override
    public boolean updateUserBalanceById(int id, double balance) throws DAOException {
        User updatedUser = userRepository.updateUserBalanceById(id, balance);
        return updatedUser.getBalance()==balance;
    }

    @Override
    public List<User> findUsersByName(String name) throws DAOException {
        return userRepository.findUsersByName(name);
    }

    @Override
    public List<User> findUsersBySurname(String surname) throws DAOException {
        return userRepository.findUsersBySurname(surname);
    }

    @Override
    public List<User> findUsersByEmail(String email) throws DAOException {
        return userRepository.findUsersByEmail(email);
    }

    @Override
    public List<User> findUsersByPhone(String phone) throws DAOException {
        return userRepository.findUsersByPhone(phone);
    }

    @Override //пересмотреть видео
    public List<User> getUsersRange(int page, int limit) throws DAOException {

        return null;
    }
}
