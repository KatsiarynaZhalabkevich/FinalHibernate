package by.epam.web.dao;

import by.epam.web.bean.Tarif;
import by.epam.web.bean.User;
import by.epam.web.config.DaoConfig;
import by.epam.web.config.HibernateConfig;
import by.epam.web.dto.UserTarif;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ExtendWith(SpringExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfig.class, DaoConfig.class})
public class TarifDaoTest {
    @Autowired
    private TarifDAO tarifDAO;

    @Test
    @Transactional
    @Rollback
    public void addTarifTest() throws DAOException {
        Tarif tarif = new Tarif();
        tarif.setName("New test tariff");
        tarif.setDiscount(0.5);
        tarif.setPrice(35.6);
        tarif.setDescription("Tariff's description");
        tarif.setSpeed(200);
       Assert.assertTrue(tarifDAO.addTarif(tarif));
    }
    @Test
    @Transactional
    @Rollback
    public void editTarifTest() throws DAOException {
        Tarif tarif = new Tarif();
        tarif.setName("New test tariff");
        tarif.setDiscount(0.5);
        tarif.setPrice(35.6);
        tarif.setDescription("Tariff's description");
        tarif.setSpeed(200);
        tarifDAO.addTarif(tarif);
        tarif.setName("Changed tariff name");
        tarifDAO.editTarif(tarif);
        Assert.assertEquals(tarif, tarifDAO.getTarifById(tarif.getId()));
    }
    @Test
    @Transactional
    @Rollback
    public void getTarifByIdTest() throws DAOException {
        Tarif tarif = new Tarif();
        tarif.setName("New test tariff");
        tarif.setDiscount(0.5);
        tarif.setPrice(35.6);
        tarif.setDescription("Tariff's description");
        tarif.setSpeed(200);
        tarifDAO.addTarif(tarif);
        Assert.assertEquals(tarif, tarifDAO.getTarifById(tarif.getId()));
    }
    @Test
    @Transactional
    @Rollback
    //нужна тестовая база
    public void getAllTest() throws DAOException {
    }
    @Test
    @Transactional
    @Rollback
    public void deleteTarifByIdTest() throws DAOException {
        Tarif tarif = new Tarif();
        tarif.setName("New test tariff");
        tarif.setDiscount(0.5);
        tarif.setPrice(35.6);
        tarif.setDescription("Tariff's description");
        tarif.setSpeed(200);
        tarifDAO.addTarif(tarif);
        Assert.assertTrue(tarifDAO.deleteTarifById(tarif.getId()));
    }
    @Test
    @Transactional
    @Rollback
    //тестовая база
    public void getTarifByUserIdTest() throws DAOException {
    }
    @Test
    @Transactional
    @Rollback
    public void getTariffRangeTest() throws DAOException {
    }

}
