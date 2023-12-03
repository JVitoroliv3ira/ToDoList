package com.example.todolist.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.R;
import com.example.todolist.models.TodoList;
import com.example.todolist.readers.TodoListReader;

public class DetailTodoListActivity extends AppCompatActivity {
    private TodoListReader todoListReader;

    private TodoList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_todo_list);

        this.todoListReader = new TodoListReader(this.getApplicationContext());
        this.list = this.todoListReader.getEntityById(this.getIntent().getIntExtra("listId", 0));
    }
}