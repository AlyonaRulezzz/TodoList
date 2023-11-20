package ru.test.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

    LinearLayout linearLayoutForTodoList;
    FloatingActionButton btnAdd;

//    private ArrayList<Note> notes = new ArrayList<>();
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
        linearLayoutForTodoList.removeAllViews();

        for (Note note : database.getNotes()) {
            View view = getLayoutInflater().inflate(R.layout.note_item, linearLayoutForTodoList, false);
            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvNoteItem = view.findViewById(R.id.tvNoteItem);

            tvNoteItem.setText(note.getText());

            int colorResId;
            switch (note.getPriority()) {
                case 0:
                    colorResId = android.R.color.holo_green_light;
                    break;
                case 1:
                    colorResId = android.R.color.holo_orange_light;
                    break;
                default:
                    colorResId = android.R.color.holo_red_light;
            }
            int color = ContextCompat.getColor(this, colorResId);
            tvNoteItem.setBackgroundColor(color);

            linearLayoutForTodoList.addView(view);
        }
    }

    private void initViews() {
        linearLayoutForTodoList = findViewById(R.id.linearLayoutForTodoList);
        btnAdd = findViewById(R.id.btnAdd);
    }
}