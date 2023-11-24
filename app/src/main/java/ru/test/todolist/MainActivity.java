package ru.test.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView rvNotes;
    private FloatingActionButton btnAdd;
    private AdapterNotes adapterNotes;
    private MainViewModel mainViewModel;

    //    private ArrayList<Note> notes = new ArrayList<>();
//    private Database database = Database.getInstance();
//    private NoteDatabase noteDatabase;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
//        noteDatabase = NoteDatabase.getInstance(getApplication());

//        LiveData<List<Note>> notes = noteDatabase.notesDAO().getNotes();
//        mainViewModel = new MainViewModel(getApplication()); //
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapterNotes.setNotes(notes);
            }
        });

        mainViewModel.getCountLD().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer count) {
                Toast.makeText(MainActivity.this, count.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        adapterNotes = new AdapterNotes();
        adapterNotes.setIonNoteClickListener(new AdapterNotes.IonNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
//                database.remove(note.getId());
//                showNotes();
                //
                mainViewModel.addAndShowCount();
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        0,
                        ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT
                ) {
                    @Override
                    public boolean onMove(
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Note note = adapterNotes.getNotes().get(position);
                        mainViewModel.remove(note.getId());
//                        database.remove(note.getId());
//                        Thread thread = new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                noteDatabase.notesDAO().remove(note.getId());
//                            }
//                        });
//                        thread.start();
                    }
                });

        itemTouchHelper.attachToRecyclerView(rvNotes);
        rvNotes.setAdapter(adapterNotes);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddNoteActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        rvNotes = findViewById(R.id.rvNotes);
        btnAdd = findViewById(R.id.btnAdd);
    }
}