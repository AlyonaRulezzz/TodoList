package ru.test.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    RecyclerView rvNotes;
    FloatingActionButton btnAdd;
    AdapterNotes adapterNotes;

    //    private ArrayList<Note> notes = new ArrayList<>();
//    private Database database = Database.getInstance();
    private NoteDatabase noteDatabase;
    private Handler handler = new Handler(Looper.getMainLooper());

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        noteDatabase = NoteDatabase.getInstance(getApplication());
        adapterNotes = new AdapterNotes();
        adapterNotes.setIonNoteClickListener(new AdapterNotes.IonNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
//                database.remove(note.getId());
//                showNotes();
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
//                        database.remove(note.getId());
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                noteDatabase.notesDAO().remove(note.getId());
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        showNotes();
                                    }
                                });
                            }
                        });
                        thread.start();
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

    @Override
    protected void onResume() {
        super.onResume();
        showNotes();
    }

    private void showNotes() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Note> notes = noteDatabase.notesDAO().getNotes();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapterNotes.setNotes(notes);
                    }
                });
            }
        });
        thread.start();
    }

    private void initViews() {
        rvNotes = findViewById(R.id.rvNotes);
        btnAdd = findViewById(R.id.btnAdd);
    }
}