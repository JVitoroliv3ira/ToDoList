package com.example.todolist.readers;

import android.content.Context;

import com.example.todolist.interfaces.IReader;
import com.example.todolist.models.Todo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TodoReader implements IReader<Integer, Todo> {
    private final Context context;

    public TodoReader(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return this.context;
    }

    @Override
    public String getFilePath() {
        return "todo.json";
    }

    @Override
    public Map<Integer, Todo> getEntities() {
        this.createFileIfNotExists();
        HashMap<Integer, Todo> todos = new HashMap<>();
        try (
                FileInputStream inputStream = this.getContext().openFileInput(this.getFilePath());
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(streamReader)
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                JSONObject object = new JSONObject(line);
                Todo todo = new Todo(
                        object.getInt("id"),
                        object.getString("title"),
                        object.getString("description"),
                        object.getBoolean("finished"),
                        object.getBoolean("priority"),
                        object.getInt("todoList")
                );
                todos.put(todo.getId(), todo);
            }
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
        }

        return todos;
    }

    public Integer getCurrentId() {
        Set<Integer> keys = this.getEntities().keySet();
        return Boolean.TRUE.equals(keys.isEmpty())
                ? 0
                : Collections.max(keys);
    }
}
