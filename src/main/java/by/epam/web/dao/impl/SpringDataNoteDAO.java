package by.epam.web.dao.impl;

import by.epam.web.bean.Note;
import by.epam.web.dao.DAOException;
import by.epam.web.dao.NoteDAO;
import by.epam.web.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpringDataNoteDAO implements NoteDAO {
    @Autowired
    private NoteRepository noteRepository;
    @Override
    public boolean addNote(Note note) throws DAOException {

        return noteRepository.save(note)!=null;
    }

    @Override
    public boolean deleteNoteById(int id) throws DAOException {
        noteRepository.deleteById(id);
        return !noteRepository.existsById(id);
    }

    @Override
    public List<Note> takeNoteByUserId(int id) throws DAOException {
        return noteRepository.getNotesByUser_Id(id);
    }

    @Override
    public List<Note> takeNoteByTarifId(int id) throws DAOException {
        return noteRepository.getNotesByTariff_Id(id);
    }
}
