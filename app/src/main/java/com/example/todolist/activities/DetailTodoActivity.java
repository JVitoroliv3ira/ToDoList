package com.example.todolist.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.example.todolist.R;
import com.example.todolist.models.Todo;
import com.example.todolist.models.TodoList;
import com.example.todolist.readers.TodoReader;
import com.example.todolist.writers.TodoWriter;

import java.util.Map;

public class DetailTodoActivity extends AppCompatActivity {

    private EditText editTextTodoTitle;
    private EditText editTextTodoDescription;
    private RadioButton radioButtonPriorityTrue;
    private RadioButton radioButtonPriorityFalse;
    private RadioButton radioButtonFinishedTrue;
    private RadioButton radioButtonFinishedFalse;
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
        this.editTextTodoTitle = findViewById(R.id.edit_text_detail_todo_title);
        this.editTextTodoDescription = findViewById(R.id.edit_text_detail_todo_description);
        this.radioButtonPriorityTrue = findViewById(R.id.radio_button_detail_todo_priority_todo_true);
        this.radioButtonPriorityFalse = findViewById(R.id.radio_button_detail_todo_priority_todo_false);
        this.radioButtonFinishedTrue = findViewById(R.id.radio_button_detail_todo_finished_todo_true);
        this.radioButtonFinishedFalse = findViewById(R.id.radio_button_detail_todo_finished_todo_false);

        this.deleteTodoButton = findViewById(R.id.delete_todo_button);

        this.todoReader = new TodoReader(this.getApplicationContext());
        this.todoWriter = new TodoWriter(this.getApplicationContext());
        this.todo = this.todoReader.getEntityById(getIntent().getIntExtra("todoId", 0));

        this.loadTodoInfo();

        this.deleteTodoButton.setOnClickListener(v -> this.handleDeleteTodoClick());
    }

    private void loadTodoInfo() {
        this.editTextTodoTitle.setText(this.todo.getTitle());
        this.editTextTodoDescription.setText(this.todo.getDescription());
        if (Boolean.TRUE.equals(this.todo.getPriority())) {
            this.radioButtonPriorityTrue.setChecked(true);
        } else {
            this.radioButtonPriorityFalse.setChecked(true);
        }
        if (Boolean.TRUE.equals(this.todo.getFinished())) {
            this.radioButtonFinishedTrue.setChecked(true);
        } else {
            this.radioButtonFinishedFalse.setChecked(true);
        }
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