package by.epam.web.dao;

import by.epam.web.dao.impl.HibernateNoteDAO;
import by.epam.web.dao.impl.HibernateTariffDAO;
import by.epam.web.dao.impl.HibernateUserDAO;


public class DAOProvider {

    private static final DAOProvider instance = new DAOProvider();
    private final UserDAO userDao = new HibernateUserDAO();
    private final TarifDAO tarifDAO = new HibernateTariffDAO();
    private final NoteDAO noteDAO = new HibernateNoteDAO();

    private DAOProvider() {
    }

    public static DAOProvider getInstance() {
        return instance;
    }

    public UserDAO getUserDao() {
        return userDao;
    }

    public TarifDAO getTarifDAO() {return tarifDAO;}

    public NoteDAO getNoteDAO(){return noteDAO;}



}
