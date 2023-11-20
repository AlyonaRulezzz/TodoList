package ru.test.todolist;

import java.util.ArrayList;
import java.util.Random;

public class Database {

    private static Database instance = null;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private Database() {
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Note note = new Note(i, "Note " + i, random.nextInt(3));
            notes.add(note);
        }
    }

    private ArrayList<Note> notes = new ArrayList<>();

    public void add(Note note) {
        notes.add(note);
    }

    public void remove(int id) {
        for (Note note : notes) {
            if (note.getId() == id) {
                notes.remove(note);
            }
        }
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }
}
