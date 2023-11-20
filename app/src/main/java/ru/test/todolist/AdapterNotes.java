package ru.test.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterNotes extends RecyclerView.Adapter<AdapterNotes.VHNotes> {
    private ArrayList<Note> notes = new ArrayList<Note>();

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterNotes.VHNotes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new VHNotes(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNotes.VHNotes holder, int position) {
        Note note = notes.get(position);

        holder.tvNoteItem.setText(note.getText());

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
        int color = ContextCompat.getColor(holder.itemView.getContext(), colorResId);
        holder.tvNoteItem.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class VHNotes extends RecyclerView.ViewHolder {
        TextView tvNoteItem;

        public VHNotes(@NonNull View itemView) {
            super(itemView);
            tvNoteItem = itemView.findViewById(R.id.tvNoteItem);
        }
    }
}
