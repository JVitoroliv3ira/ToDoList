package com.example.todolist.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.R;
import com.example.todolist.models.TodoList;
import com.example.todolist.readers.TodoListReader;
import com.example.todolist.writers.TodoListWriter;

import java.util.Map;
import java.util.Objects;

public class CreateTodoListActivity extends AppCompatActivity {
    private TodoListWriter writer;
    private TodoListReader reader;
    private Button buttonCreateTodoList;
    private Button buttonBackMainActivity;
    private EditText editTextTitle;
    private EditText editTextDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo_list);

        this.reader = new TodoListReader(this.getApplicationContext());
        this.writer = new TodoListWriter(this.getApplicationContext());

        this.editTextTitle = findViewById(R.id.edit_text_create_todo_list_title);
        this.editTextDescription = findViewById(R.id.edit_text_create_todo_list_description);
        this.buttonCreateTodoList = findViewById(R.id.button_create_todo_list);
        this.buttonBackMainActivity = findViewById(R.id.button_back_to_main_activity);

        this.buttonCreateTodoList.setOnClickListener(v -> this.handleCreateClick());
        this.buttonBackMainActivity.setOnClickListener(v -> this.goToMainActivity());
    }

    private void handleCreateClick() {
        String title = this.editTextTitle.getText().toString();
        String description = this.editTextDescription.getText().toString();
        if (Objects.isNull(title) || title.trim().isEmpty()) {
            Toast.makeText(
                    this.getApplicationContext(),
                    "Informe um título",
                    Toast.LENGTH_SHORT
            ).show();
        } else if (Objects.isNull(description) || description.trim().isEmpty()) {
            Toast.makeText(
                    this.getApplicationContext(),
                    "Informe uma descrição",
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            this.createTodoList(title, description);
        }
    }

    private void createTodoList(String title, String description) {
        Integer id = this.reader.getCurrentId() + 1;
        TodoList list = new TodoList(id, title, description);
        Map<Integer, TodoList> entities = this.reader.getEntities();
        entities.put(list.getId(), list);
        this.writer.saveEntities(entities);
        this.goToMainActivity();
    }

    private void goToMainActivity() {
        this.finish();
    }
}