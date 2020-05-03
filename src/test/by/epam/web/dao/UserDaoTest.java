package by.epam.web.dao;


import by.epam.web.bean.User;
import by.epam.web.config.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@ExtendWith(SpringExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfig.class, DaoConfig.class})
public class UserDaoTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    @Transactional
    @Rollback
  public void addUserTest() throws DAOException {
        User user = new User();
        user.setName("Kate");
        user.setSurname("Beliuzhenka");
        Assert.assertTrue(userDAO.addUser(user));

    }
    @Test
    @Transactional
    @Rollback
   public void findUserByLoginTest() throws DAOException {
       User user = new User();
       user.setName("Kate");
       user.setSurname("Beliuzhenka");
       user.setLogin("kate");
       userDAO.addUser(user);
       User userFromBD = userDAO.findUserByLogin(user.getLogin());
       Assert.assertEquals(user, userFromBD);
   }
    @Test
    @Transactional
    @Rollback
   public void findUserByIdTest() throws DAOException {
       User user = new User();
       user.setName("Kate");
       user.setSurname("Beliuzhenka");
       userDAO.addUser(user);
       User userFromBD = userDAO.findUserById(user.getId());
       Assert.assertEquals(user, userFromBD);
    }
    @Test
    @Transactional
    @Rollback
    public void isLoginExistTest() throws DAOException{
        User user = new User();
        user.setName("Kate");
        user.setSurname("Beliuzhenka");
        user.setLogin("kate");
        userDAO.addUser(user);
        Assert.assertTrue(userDAO.isLoginExist(user.getLogin()));
    }
//нужна тестовая база
    public void takeAllUserTest() throws DAOException{}

    @Test
    @Transactional
    @Rollback
    public void updateUserInfoTest() throws DAOException{
        User user = new User();
        user.setName("Kate");
        user.setSurname("Beliuzhenka");
        user.setLogin("kate");
        userDAO.addUser(user);
        User userFromBD = userDAO.findUserById(user.getId());
        userFromBD.setLogin("newLogin");
        userFromBD.setPhone("+375291234578");
        userDAO.updateUserInfo(userFromBD);
        Assert.assertEquals(userFromBD,userDAO.findUserById(userFromBD.getId()));
    }
    @Test
    @Transactional
    @Rollback
    public void deleteUserTest() throws DAOException{
        User user = new User();
        user.setName("Kate");
        user.setSurname("Beliuzhenka");
        user.setLogin("kate");
        userDAO.addUser(user);
        Assert.assertTrue(userDAO.deleteUser(user.getId()));
    }
    @Test
    @Transactional
    @Rollback
    public void getPasswordByIdTest() throws DAOException{
        User user = new User();
        user.setName("Kate");
        user.setSurname("Beliuzhenka");
        user.setLogin("kate");
        user.setPassword("password");
        userDAO.addUser(user);
        Assert.assertEquals("password", userDAO.getPasswordById(user.getId()));
    }
    @Test
    @Transactional
    @Rollback
    public void updatePasswordTest() throws DAOException{
        User user = new User();
        user.setName("Kate");
        user.setSurname("Beliuzhenka");
        user.setLogin("kate");
        user.setPassword("password");
        userDAO.addUser(user);
        User userFromBD = userDAO.findUserById(user.getId());
        userFromBD.setPassword("newPassword");
        userDAO.updatePassword(userFromBD);
        Assert.assertEquals(userFromBD, userDAO.findUserById(userFromBD.getId()) );
    }
    @Test
    @Transactional
    @Rollback
    public void updateIsActiveTest() throws DAOException{}
    @Test
    @Transactional
    @Rollback
    public void updateUserBalanceByIdTest() throws DAOException{}
    @Test
    @Transactional
    @Rollback
    public void getUsersRangeTest() throws DAOException{}
    @Test
    @Transactional
    @Rollback
    public void findUsersByNameTest() throws DAOException{}
    @Test
    @Transactional
    @Rollback
    public void findUsersBySurnameTest() throws DAOException{}
    @Test
    @Transactional
    @Rollback
    public void findUsersByEmailTest() throws DAOException{}
    @Test
    @Transactional
    @Rollback
    public void findUsersByPhoneTest() throws DAOException{}

}
