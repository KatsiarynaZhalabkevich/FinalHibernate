package by.epam.web.repository;

import by.epam.web.bean.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NoteRepository extends CrudRepository<Note, Integer>, JpaRepository<Note, Integer> {

    List<Note> getNotesByUser_Id(int id);

    List<Note> getNotesByTariff_Id(int id);
}
