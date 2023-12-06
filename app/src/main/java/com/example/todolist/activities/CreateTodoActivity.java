package com.example.todolist.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.R;
import com.example.todolist.models.Todo;
import com.example.todolist.readers.TodoReader;
import com.example.todolist.writers.TodoWriter;

import java.util.Map;
import java.util.Objects;

public class CreateTodoActivity extends AppCompatActivity {

    private TodoWriter writer;
    private TodoReader reader;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private RadioButton radioButtonPriorityTrue;
    private Button buttonBackToDetailTodoListActivity;
    private Button buttonCreateTodo;

    private Integer todoListId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo);

        this.todoListId = this.getIntent().getIntExtra("listId", 0);

        this.writer = new TodoWriter(this.getApplicationContext());
        this.reader = new TodoReader(this.getApplicationContext());

        this.editTextTitle = findViewById(R.id.edit_text_create_todo_title);
        this.editTextDescription = findViewById(R.id.edit_text_create_todo_description);

        this.radioButtonPriorityTrue = findViewById(R.id.radio_button_create_todo_priority_true);

        this.buttonCreateTodo = findViewById(R.id.button_create_todo);
        this.buttonCreateTodo.setOnClickListener(v -> this.handleCreateClick());

        this.buttonBackToDetailTodoListActivity = findViewById(R.id.button_back_to_detail_todo_list_activity);
        this.buttonBackToDetailTodoListActivity.setOnClickListener(v -> this.backToDetailTodoListActivity());
    }

    private void handleCreateClick() {
        String title = this.editTextTitle.getText().toString();
        String description = this.editTextDescription.getText().toString();
        Boolean priority = this.radioButtonPriorityTrue.isChecked();

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
            this.createTodo(title, description, priority);
        }
    }

    private void createTodo(String title, String description, Boolean priority) {
        Integer id = this.reader.getCurrentId() + 1;
        Todo todo = new Todo(
                id,
                title,
                description,
                false,
                priority,
                todoListId
        );
        Map<Integer, Todo> entities = this.reader.getEntities();
        entities.put(todo.getId(), todo);
        this.writer.saveEntities(entities);
        Toast.makeText(this.getApplicationContext(), "Tarefa cadastrada com sucesso.", Toast.LENGTH_SHORT).show();
        this.backToDetailTodoListActivity();
    }

    private void backToDetailTodoListActivity() {
        this.finish();
    }
}