package ru.test.todolist;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {
    private NoteDatabase noteDatabase;
    private int count = 0;
    private MutableLiveData<Integer> countLD = new MutableLiveData<>();
//    private MutableLiveData<List<Note>> notes = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainViewModel(@NonNull Application application) {
        super(application);
        noteDatabase = NoteDatabase.getInstance(getApplication());
    }

    public LiveData<List<Note>> getNotes() {
        return noteDatabase.notesDAO().getNotes();
//        return notes;
    }

//    public void refreshNotes() {
//        Disposable disposable = noteDatabase.notesDAO().getNotes()
////        Disposable disposable = getNotesRx()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<List<Note>>() {
//                               @Override
//                               public void accept(List<Note> updatedNotesFromDB) throws Throwable {
//                                   notes.setValue(updatedNotesFromDB);
//                               }
//                           },
//                        new Consumer<Throwable>() {
//                            @Override
//                            public void accept(Throwable throwable) throws Throwable {
//                                Log.d("MainViewModel", "getNotesRx load error");
//                            }
//                        });
//        compositeDisposable.add(disposable);
//    }
    
//    private Single getNotesRx() {
//        return Single.fromCallable(new Callable<List<Note>>() {
//            @Override
//            public List<Note> call() throws Exception {
//                return noteDatabase.notesDAO().getNotes();
////                throw new Exception(); //
//            }
//        });
//    }

    public void remove(int id) {
        Disposable disposable = noteDatabase.notesDAO().remove(id)
//        Disposable disposable = removeRx(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                               @Override
                               public void run() throws Throwable {
                                   Log.d("MainViewModel", "remove" + id);
//                                   refreshNotes();
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {
                                Log.d("MainViewModel", "remove error for id = " + id);
                            }
                        });
        compositeDisposable.add(disposable);
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                noteDatabase.notesDAO().remove(id);
//            }
//        });
//        thread.start();
    }
    
//    private Completable removeRx(int id) {
//        return Completable.fromAction(new Action() {
//            @Override
//            public void run() throws Throwable {
//                noteDatabase.notesDAO().remove(id);
////                throw new Exception(); //
//            }
//        });
//    }

    public void addAndShowCount() {
        count++;
        countLD.setValue(count);
    }

    public LiveData<Integer> getCountLD() {
        return countLD;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
