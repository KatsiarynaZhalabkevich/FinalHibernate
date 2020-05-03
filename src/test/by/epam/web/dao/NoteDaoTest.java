package by.epam.web.dao;

import by.epam.web.bean.Note;
import by.epam.web.bean.Tarif;
import by.epam.web.bean.User;
import by.epam.web.config.DaoConfig;
import by.epam.web.config.HibernateConfig;
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
public class NoteDaoTest {
    @Autowired
    NoteDAO noteDAO;


    @Test
    @Transactional
    @Rollback
    public void addNoteTest() throws DAOException {

//        User user = new User();
//        user.setName("Kate");
//        user.setSurname("Beliuzhenka");
//
//        Tarif tarif = new Tarif();
//        tarif.setName("New test tariff");
//        tarif.setDiscount(0.5);
//        tarif.setPrice(35.6);
//        tarif.setDescription("Tariff's description");
//        tarif.setSpeed(200);

        Note note = new Note();
//        note.setUser(user);
//        note.setTariff(tarif);

        Assert.assertTrue(noteDAO.addNote(note));
    }

    @Test
    @Transactional
    @Rollback
    public void deleteNoteByIdTest() throws DAOException {
        Note note = new Note();
        noteDAO.addNote(note);
        Assert.assertTrue(noteDAO.deleteNoteById(note.getId()));

    }

    @Test
    @Transactional
    @Rollback
    public void takeNoteByUserIdTest() throws DAOException {
    }

    @Test
    @Transactional
    @Rollback
    public void takeNoteByTarifIdTest() throws DAOException {
    }
}
