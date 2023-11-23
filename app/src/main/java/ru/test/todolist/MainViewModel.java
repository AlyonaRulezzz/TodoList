package ru.test.todolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private NoteDatabase noteDatabase;

    public MainViewModel(@NonNull Application application) {
        super(application);
        noteDatabase = NoteDatabase.getInstance(getApplication());
    }

    public LiveData<List<Note>> getNotes() {
        return noteDatabase.notesDAO().getNotes();
    }

    public void remove(int id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                noteDatabase.notesDAO().remove(id);
            }
        });
        thread.start();
    }
}
