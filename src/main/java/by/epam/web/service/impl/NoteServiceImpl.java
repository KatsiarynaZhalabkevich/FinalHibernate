package by.epam.web.service.impl;

import by.epam.web.bean.Note;
import by.epam.web.dao.DAOException;
import by.epam.web.dao.NoteDAO;
import by.epam.web.service.NoteService;
import by.epam.web.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class NoteServiceImpl implements NoteService {
    private final static Logger logger = LogManager.getLogger();
    @Autowired
    private static NoteDAO noteDAO;
    @Override
    public boolean addNote(Note note) throws ServiceException {
        boolean flag;

        try {
            flag = noteDAO.addNote(note);

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return flag;
    }

    @Override
    public boolean deleteNote(int id) throws ServiceException {
        boolean flag;
        try {
            flag=noteDAO.deleteNoteById(id);
        } catch (DAOException e) {
           throw new ServiceException(e);
        }
        return flag;
    }

    @Override
    public List<Note> findNoteByTarifId(int id) throws ServiceException {
        List<Note> notes = new ArrayList<>();
        try {
           notes.addAll( noteDAO.takeNoteByTarifId(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return notes;
    }

    @Override
    public List<Note> findNoteByUserId(int id) throws ServiceException {
        List<Note> notes = new ArrayList<>();
        try {
            notes.addAll( noteDAO.takeNoteByUserId(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return notes;
    }
}
