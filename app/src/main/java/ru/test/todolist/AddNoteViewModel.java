package ru.test.todolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AddNoteViewModel extends AndroidViewModel {
    private NotesDAO notesDAO;
    private MutableLiveData<Boolean> shouldCloseAddNoteActivity = new MutableLiveData<>();

    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        notesDAO = NoteDatabase.getInstance(getApplication()).notesDAO();
    }

    public void add(Note note) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
//                noteDatabase.notesDAO().add(new Note(0, text, priority));
//                addNoteViewModel.add(new Note(0, text, priority));
                notesDAO.add(note);
                shouldCloseAddNoteActivity.postValue(true);
            }
        });
        thread.start();
    }

    public LiveData<Boolean> getShouldCloseAddNoteActivity() {
        return shouldCloseAddNoteActivity;
    }
}
