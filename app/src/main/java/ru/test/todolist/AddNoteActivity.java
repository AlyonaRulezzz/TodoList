package ru.test.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class AddNoteActivity extends AppCompatActivity {

    private EditText etNote;
    private RadioButton rbLowPriority;
    private RadioButton rbMiddlePriority;
    private Button btnSave;
    AddNoteViewModel addNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addn_note);

        initViews();
        addNoteViewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);
        addNoteViewModel.getShouldCloseAddNoteActivity().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldCloseAddNoteActivity) {
                if (shouldCloseAddNoteActivity) {
                    finish();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSaveOnClick();
            }
        });
    }

    private void btnSaveOnClick() {
        String text = etNote.getText().toString().trim();
        int priority = getPriority();
        addNoteViewModel.add(new Note(0, text, priority));
    }

    private int getPriority() {
        int priority;
        if (rbLowPriority.isChecked()) {
            priority = 0;
        } else if (rbMiddlePriority.isChecked()) {
            priority = 1;
        } else {
            priority = 2;
        }
        return priority;
    }

    private void initViews() {
        etNote = findViewById(R.id.etNote);
        rbLowPriority = findViewById(R.id.rbLowPriority);
        rbMiddlePriority = findViewById(R.id.rbMiddlePriority);
        btnSave = findViewById(R.id.btnSave);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddNoteActivity.class);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}