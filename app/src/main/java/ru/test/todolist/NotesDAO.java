package ru.test.todolist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface NotesDAO {
    @Query("SELECT * FROM NOTES")
    LiveData<List<Note>> getNotes();
//    Single<List<Note>> getNotes();
//    List<Note> getNotes();

    @Insert
//    void add(Note note);
    Completable add(Note note);

    @Query("DELETE FROM NOTES WHERE id = :id")
//    void remove(int id);
    Completable remove(int id);
}
