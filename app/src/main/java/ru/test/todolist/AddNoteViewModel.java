package ru.test.todolist;

import android.app.Application;
import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddNoteViewModel extends AndroidViewModel {
    private NotesDAO notesDAO;
    private MutableLiveData<Boolean> shouldCloseAddNoteActivity = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        notesDAO = NoteDatabase.getInstance(getApplication()).notesDAO();
    }

    public void add(Note note) {
                Disposable disposable = notesDAO.add(note)
                        .subscribeOn(Schedulers.io())
//                        .delay(5, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action() {
                            @Override
                            public void run() throws Throwable {
                                shouldCloseAddNoteActivity.setValue(true);
                                Log.d("AddNoteViewModel", "subscribe");
                            }
                        });
                compositeDisposable.add(disposable);
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                notesDAO.add(note);
//                shouldCloseAddNoteActivity.postValue(true);
//            }
//        });
//        thread.start();
    }

    public LiveData<Boolean> getShouldCloseAddNoteActivity() {
        return shouldCloseAddNoteActivity;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
