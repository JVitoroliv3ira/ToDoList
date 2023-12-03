package com.example.todolist.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.interfaces.IOnCardClickListener;
import com.example.todolist.models.TodoList;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder> {
    private final List<TodoList> todoLists;
    private IOnCardClickListener listener;

    public void setOnCardClickListener(IOnCardClickListener listener) {
        this.listener = listener;
    }
    public static class TodoListViewHolder extends RecyclerView.ViewHolder {
        public TextView listTitle;
        public TextView listDescription;
        public TodoListViewHolder(@NonNull View v, final IOnCardClickListener listener) {
            super(v);
            this.listTitle = v.findViewById(R.id.list_title);
            this.listDescription = v.findViewById(R.id.list_description);
            v.setOnClickListener(c -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onCardClick(getAdapterPosition());
                }
            });
        }
    }

    public TodoListAdapter(List<TodoList> todoLists) {
        this.todoLists = todoLists;
    }

    @NonNull
    @Override
    public TodoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card, parent, false);
        return new TodoListViewHolder(v, this.listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListViewHolder holder, int position) {
        TodoList list = this.todoLists.get(position);
        holder.listTitle.setText(list.getTitle());
        holder.listDescription.setText(list.getDescription());
    }

    @Override
    public int getItemCount() {
        return this.todoLists.size();
    }
}
