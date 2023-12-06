package com.example.todolist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.R;
import com.example.todolist.models.TodoList;
import com.example.todolist.readers.TodoListReader;
import com.example.todolist.writers.TodoListWriter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Map;

public class DetailTodoListActivity extends AppCompatActivity {
    private EditText editTextTodoListTitle;
    private EditText editTextTodoListDescription;
    private ImageButton deleteTodoListButton;

    private FloatingActionButton createTodoButton;
    private TodoListReader todoListReader;
    private TodoListWriter todoListWriter;
    private TodoList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_todo_list);

        this.todoListReader = new TodoListReader(this.getApplicationContext());
        this.todoListWriter = new TodoListWriter(this.getApplicationContext());
        this.list = this.todoListReader.getEntityById(this.getIntent().getIntExtra("listId", 0));


         this.editTextTodoListTitle = this.findViewById(R.id.edit_text_detail_todo_list_title);
         this.editTextTodoListDescription = this.findViewById(R.id.edit_text_detail_todo_list_description);
         this.editTextTodoListTitle.setText(list.getTitle());
         this.editTextTodoListDescription.setText(list.getDescription());

         this.deleteTodoListButton = this.findViewById(R.id.delete_todo_list_button);
         this.deleteTodoListButton.setOnClickListener(v -> this.handleDeleteTodoListClick());

         this.createTodoButton = this.findViewById(R.id.button_go_to_create_todo);
         this.createTodoButton.setOnClickListener(v -> this.goToCreateTodoActivity());
    }

    private void handleDeleteTodoListClick() {
        Map<Integer, TodoList> lists = this.todoListReader.getEntities();
        lists.remove(this.list.getId());
        this.todoListWriter.saveEntities(lists);
        Toast.makeText(this.getApplicationContext(), "Lista deletada com sucesso.", Toast.LENGTH_SHORT).show();
        this.goToMainActivity();
    }

    private void goToMainActivity() {
        this.finish();
    }

    private void goToCreateTodoActivity() {
        Intent intent = new Intent(this, CreateTodoActivity.class);
        intent.putExtra("listId", this.list.getId());
        startActivity(intent);
    }
}