package ru.test.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    RecyclerView rvNotes;
    FloatingActionButton btnAdd;

    private Database database = Database.getInstance();

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddnNoteActivity.newIntent(MainActivity.this);
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
        rvNotes.removeAllViews();

        for (Note note : database.getNotes()) {
//            View view = getLayoutInflater().inflate(R.layout.note_item, rvNotes, false);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    database.remove(note.getId());
                    showNotes();
                }
            });

            rvNotes.addView(view);
        }
    }

    private void initViews() {
        rvNotes = findViewById(R.id.rvNotes);
        btnAdd = findViewById(R.id.btnAdd);
    }
}