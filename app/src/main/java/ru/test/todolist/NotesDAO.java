package ru.test.todolist;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotesDAO {
    @Query("SELECT * FROM NOTES")
    List<Note> getNotes();

    @Insert
    void add(Note note);

    @Query("DELETE FROM NOTES WHERE id = :id")
    void remove(int id);
}
