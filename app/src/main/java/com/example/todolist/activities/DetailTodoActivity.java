package com.example.todolist.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.todolist.R;
import com.example.todolist.models.Todo;
import com.example.todolist.models.TodoList;
import com.example.todolist.readers.TodoReader;
import com.example.todolist.writers.TodoWriter;

import java.util.Map;

public class DetailTodoActivity extends AppCompatActivity {

    private ImageButton deleteTodoButton;

    private TodoReader todoReader;
    private TodoWriter todoWriter;
    private Todo todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_todo);
        this.initializeComponents();
    }

    private void initializeComponents() {
        this.deleteTodoButton = findViewById(R.id.delete_todo_button);

        this.todoReader = new TodoReader(this.getApplicationContext());
        this.todoWriter = new TodoWriter(this.getApplicationContext());
        this.todo = this.todoReader.getEntityById(getIntent().getIntExtra("todoId", 0));

        this.deleteTodoButton.setOnClickListener(v -> this.handleDeleteTodoClick());
    }

    private void handleDeleteTodoClick() {
        Map<Integer, Todo> todos = this.todoReader.getEntities();
        todos.remove(this.todo.getId());
        this.todoWriter.saveEntities(todos);
        this.goToDetailTodoListActivity();
    }

    private void goToDetailTodoListActivity() {
        finish();
    }
}