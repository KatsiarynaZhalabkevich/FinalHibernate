package by.epam.web.dao.impl;

import by.epam.web.bean.Note;
import by.epam.web.bean.Tarif;
import by.epam.web.dao.BaseDao;
import by.epam.web.dao.DAOException;
import by.epam.web.dao.NoteDAO;
import by.epam.web.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.RollbackException;
import java.util.List;

//@Repository
public class HibernateNoteDAO implements NoteDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean addNote(Note note) throws DAOException {

        try {
            em.persist(note);
            return true;
        } catch (RollbackException e) {
            throw new DAOException(e);
        }


    }

    @Override
    public boolean deleteNoteById(int id) throws DAOException {
       Session session = em.unwrap(Session.class);
        try {

            Query query = session.createQuery("delete from Note where id= :id")
                    .setParameter("id", id);
            int count = query.executeUpdate();

             return count == 1;

        } catch (RollbackException e) {

            throw new DAOException(e);
        }

    }

    @Override
    public List<Note> takeNoteByUserId(int id) throws DAOException {
        Session session = em.unwrap(Session.class);
        try {

            Query query = session.createQuery("from Note where id = :id")
                    .setParameter("id", id);
            List<Note> notes = query.getResultList();
            return notes;
        } catch (HibernateException e) {

            throw new DAOException(e);
        }

    }

    @Override
    public List<Note> takeNoteByTarifId(int id) throws DAOException {
        Session session = em.unwrap(Session.class);
        try {
            session.getTransaction().begin();
            Query query = session.createQuery("from Note where user= :id")
                    .setParameter("id", id);
            List<Note> notes = query.getResultList();
            session.clear();
            session.close();

            return notes;
        } catch (HibernateException e) {
            session.close();
            throw new DAOException(e);
        }

    }
}
