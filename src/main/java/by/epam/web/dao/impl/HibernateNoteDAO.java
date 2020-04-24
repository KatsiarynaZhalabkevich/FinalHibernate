package by.epam.web.dao.impl;

import by.epam.web.bean.Note;
import by.epam.web.dao.DAOException;
import by.epam.web.dao.NoteDAO;
import by.epam.web.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;


import javax.persistence.RollbackException;
import java.util.List;

public class HibernateNoteDAO implements NoteDAO {
    @Override
    public boolean addNote(Note note) throws DAOException {
        Session session = HibernateUtil.getSession();
        try {
            session.getTransaction().begin();
            session.persist(note);
            session.getTransaction().commit();
            session.clear();
            session.close();
            return true;
        }catch (RollbackException e){
            session.getTransaction().rollback();
            session.close();
            throw new DAOException(e);
        }


    }

    @Override
    public boolean deleteNoteById(int id) throws DAOException {
        Session session = HibernateUtil.getSession();
        try {
            session.getTransaction().begin();
            Query query = session.createQuery("delete from Note where id= :id")
                    .setParameter("id", id);
            int count = query.executeUpdate();
            session.clear();
            session.close();
            return count == 1;
        }catch (RollbackException e){
            session.getTransaction().rollback();
            session.close();
            throw new DAOException(e);
        }

    }

    @Override
    public List<Note> takeNoteByUserId(int id) throws DAOException {
        Session session = HibernateUtil.getSession();
        try {
            session.getTransaction().begin();
            Query query = session.createQuery("from Note where id = :id")
                    .setParameter("id",id);
            List<Note> notes = query.getResultList();
            session.clear();
            session.close();

            return notes;
        }catch (HibernateException e){
            session.close();
            throw new DAOException(e);
        }

    }

    @Override
    public List<Note> takeNoteByTarifId(int id) throws DAOException {
        Session session = HibernateUtil.getSession();
        try {
            session.getTransaction().begin();
            Query query = session.createQuery("from Note where user= :id")
                    .setParameter("id", id);
            List<Note> notes = query.getResultList();
            session.clear();
            session.close();

            return notes;
        }catch (HibernateException e){
            session.close();
            throw new DAOException(e);
        }

    }
}
