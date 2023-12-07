package com.example.todolist.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.interfaces.ICheckBoxClickListener;
import com.example.todolist.interfaces.IOnCardClickListener;
import com.example.todolist.models.Todo;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private final List<Todo> todos;
    private IOnCardClickListener cardClickListener;
    private ICheckBoxClickListener checkBoxClickListener;

    public void setCardClickListener(IOnCardClickListener cardClickListener) {
        this.cardClickListener = cardClickListener;
    }

    public void setCheckBoxClickListener(ICheckBoxClickListener checkBoxClickListener) {
        this.checkBoxClickListener = checkBoxClickListener;
    }

    public void setOnCardClickListener(IOnCardClickListener cardClickListener, ICheckBoxClickListener checkBoxClickListener) {
        this.cardClickListener = cardClickListener;
        this.checkBoxClickListener = checkBoxClickListener;
    }
    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        public TextView todoTitle;
        public TextView todoDescription;
        public CheckBox checkBox;

        public ImageView imageView;
        public TodoViewHolder(@NonNull View v, final IOnCardClickListener cardClickListener, final ICheckBoxClickListener checkBoxClickListener) {
            super(v);
            this.todoTitle = v.findViewById(R.id.card_todo_title);
            this.todoDescription = v.findViewById(R.id.card_todo_description);
            this.checkBox = v.findViewById(R.id.todo_check_box);
            this.imageView = v.findViewById(R.id.alert_icon);
            v.setOnClickListener(c -> {
                if (cardClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    cardClickListener.onCardClick(getAdapterPosition());
                }
            });
            this.checkBox.setOnClickListener(k -> {
                if (checkBoxClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    checkBoxClickListener.onCheckBoxClick(getAdapterPosition());
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
        return new TodoViewHolder(v, this.cardClickListener, this.checkBoxClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = this.todos.get(position);
        holder.todoTitle.setText(todo.getTitle());
        holder.todoDescription.setText(todo.getDescription());
        holder.checkBox.setChecked(todo.getFinished());
        if (!todo.getPriority()) {
            holder.imageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return this.todos.size();
    }
}
