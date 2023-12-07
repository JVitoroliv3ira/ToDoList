package com.example.todolist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.interfaces.ICheckBoxClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.todolist.R;
import com.example.todolist.adapters.TodoAdapter;
import com.example.todolist.interfaces.IOnCardClickListener;
import com.example.todolist.models.Todo;
import com.example.todolist.models.TodoList;
import com.example.todolist.readers.TodoListReader;
import com.example.todolist.readers.TodoReader;
import com.example.todolist.writers.TodoWriter;
import com.example.todolist.writers.TodoListWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class DetailTodoListActivity extends AppCompatActivity implements IOnCardClickListener, ICheckBoxClickListener {

    private EditText editTextTodoListTitle, editTextTodoListDescription;
    private ImageButton deleteTodoListButton;
    private FloatingActionButton createTodoButton;
    private RecyclerView todoRecyclerView;

    private TodoListReader todoListReader;
    private TodoListWriter todoListWriter;
    private TodoReader todoReader;
    private TodoWriter todoWriter;
    private TodoList list;

    private List<Todo> todos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_todo_list);
        initializeComponents();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadTodos();
        setupCards();
    }

    private void initializeComponents() {
        editTextTodoListTitle = findViewById(R.id.edit_text_detail_todo_list_title);
        editTextTodoListDescription = findViewById(R.id.edit_text_detail_todo_list_description);
        deleteTodoListButton = findViewById(R.id.delete_todo_list_button);
        createTodoButton = findViewById(R.id.button_go_to_create_todo);
        todoRecyclerView = findViewById(R.id.list_todo_cards);

        deleteTodoListButton.setOnClickListener(v -> handleDeleteTodoListClick());
        createTodoButton.setOnClickListener(v -> goToCreateTodoActivity());

        todoListReader = new TodoListReader(getApplicationContext());
        todoListWriter = new TodoListWriter(getApplicationContext());
        todoReader = new TodoReader(getApplicationContext());
        todoWriter = new TodoWriter(getApplicationContext());

        list = todoListReader.getEntityById(getIntent().getIntExtra("listId", 0));
        editTextTodoListTitle.setText(list.getTitle());
        editTextTodoListDescription.setText(list.getDescription());
    }

    private void handleDeleteTodoListClick() {
        Map<Integer, Todo> todos = todoReader.getEntities();
        List<Integer> todoIdsToDelete = new ArrayList<>();

        for (Todo todo : todos.values()) {
            if (Objects.nonNull(todo) && todo.getTodoList().equals(list.getId())) {
                todoIdsToDelete.add(todo.getId());
            }
        }

        for (Integer id : todoIdsToDelete) {
            todos.remove(id);
        }
        todoWriter.saveEntities(todos);

        Map<Integer, TodoList> lists = todoListReader.getEntities();
        lists.remove(list.getId());
        todoListWriter.saveEntities(lists);

        Toast.makeText(getApplicationContext(), "Lista e tarefas associadas deletadas com sucesso.", Toast.LENGTH_SHORT).show();

        goToMainActivity();
    }

    private void goToMainActivity() {
        finish();
    }

    private void goToCreateTodoActivity() {
        Intent intent = new Intent(this, CreateTodoActivity.class);
        intent.putExtra("listId", list.getId());
        startActivity(intent);
    }

    private void loadTodos() {
        todos.clear();
        for (Todo todo : todoReader.getEntities().values()) {
            if (Objects.nonNull(todo) && todo.getTodoList().equals(list.getId())) {
                todos.add(todo);
            }
        }
    }

    private void setupCards() {
        todoRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        todoRecyclerView.setLayoutManager(manager);
        TodoAdapter adapter = new TodoAdapter(todos);
        adapter.setCardClickListener(this);
        adapter.setCheckBoxClickListener(this);
        todoRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onCardClick(int position) {
        Todo todo = this.todos.get(position);
        this.goToDetailTodoActivity(todo.getId());
    }

    private void goToDetailTodoActivity(Integer todoId) {
        Intent intent = new Intent(this, DetailTodoActivity.class);
        intent.putExtra("todoId", todoId);
        startActivity(intent);
    }

    @Override
    public void onCheckBoxClick(int position) {
        Todo todo = this.todos.get(position);
        todo.setFinished(!todo.getFinished());
        Map<Integer, Todo> todoMap = this.todoReader.getEntities();
        todoMap.put(todo.getId(), todo);
        this.todoWriter.saveEntities(todoMap);
    }
}
