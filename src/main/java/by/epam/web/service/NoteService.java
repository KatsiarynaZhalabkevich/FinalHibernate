package by.epam.web.service;

import by.epam.web.bean.Note;

import java.util.List;

public interface NoteService {
    boolean addNote(Note note) throws ServiceException;
    boolean deleteNote(int id) throws  ServiceException;
    List<Note> findNoteByTarifId(int id) throws  ServiceException;
    List<Note> findNoteByUserId(int id) throws  ServiceException;
}
