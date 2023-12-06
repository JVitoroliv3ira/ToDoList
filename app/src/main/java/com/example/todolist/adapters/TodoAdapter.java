package com.example.todolist.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.interfaces.IOnCardClickListener;
import com.example.todolist.models.Todo;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private final List<Todo> todos;
    private IOnCardClickListener listener;

    public void setOnCardClickListener(IOnCardClickListener listener) {
        this.listener = listener;
    }
    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        public TextView listTitle;
        public TodoViewHolder(@NonNull View v, final IOnCardClickListener listener) {
            super(v);
            this.listTitle = v.findViewById(R.id.card_todo_title);
            v.setOnClickListener(c -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onCardClick(getAdapterPosition());
                }
            });
        }
    }

    public TodoAdapter(List<Todo> todos) {
        this.todos = todos;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_card, parent, false);
        return new TodoViewHolder(v, this.listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = this.todos.get(position);
        holder.listTitle.setText(todo.getTitle());
    }

    @Override
    public int getItemCount() {
        return this.todos.size();
    }
}
