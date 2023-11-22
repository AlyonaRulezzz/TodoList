package ru.test.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    RecyclerView rvNotes;
    FloatingActionButton btnAdd;
    AdapterNotes adapterNotes;

    //    private ArrayList<Note> notes = new ArrayList<>();
//    private Database database = Database.getInstance();
    private NoteDatabase noteDatabase;

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
                        noteDatabase.notesDAO().remove(note.getId());
                        showNotes();
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
        adapterNotes.setNotes(noteDatabase.notesDAO().getNotes());
    }

    private void initViews() {
        rvNotes = findViewById(R.id.rvNotes);
        btnAdd = findViewById(R.id.btnAdd);
    }
}