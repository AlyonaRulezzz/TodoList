package ru.test.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
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

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Note note = new Note(i, "Note " + i, random.nextInt(3));

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