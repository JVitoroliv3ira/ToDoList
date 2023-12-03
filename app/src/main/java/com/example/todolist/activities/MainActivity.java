package com.example.todolist.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.adapters.TodoListAdapter;
import com.example.todolist.models.TodoList;
import com.example.todolist.readers.TodoListReader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TodoListReader reader;
    private List<TodoList> list;
    private RecyclerView todoListReciclerView;
    private FloatingActionButton createTodoListButton;

    @Override
    protected void onStart() {
        super.onStart();
        this.setupList();
        this.setupCards();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.reader = new TodoListReader(this.getApplicationContext());
        this.todoListReciclerView = this.findViewById(R.id.list_todo_list_cards);
        this.createTodoListButton = this.findViewById(R.id.button_go_to_create_todo_list);

        this.createTodoListButton.setOnClickListener(v -> this.goToCreateTodoListActivity());
    }

    private void setupList() {
        this.list = new ArrayList<>(this.reader.getEntities().values());
    }

    private void setupCards() {
        this.todoListReciclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        this.todoListReciclerView.setLayoutManager(manager);
        TodoListAdapter adapter = new TodoListAdapter(this.list);
        this.todoListReciclerView.setAdapter(adapter);
    }

    private void goToCreateTodoListActivity() {
        Intent intent = new Intent(this, CadastrarTodoListActivity.class);
        startActivity(intent);
    }
}