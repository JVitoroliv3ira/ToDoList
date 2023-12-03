package com.example.todolist.readers;

import android.content.Context;

import com.example.todolist.interfaces.IReader;
import com.example.todolist.models.TodoList;

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

public class TodoListReader implements IReader<Integer, TodoList> {

    private final Context context;

    public TodoListReader(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return this.context;
    }

    @Override
    public String getFilePath() {
        return "lists.json";
    }

    @Override
    public Map<Integer, TodoList> getEntities() {
        this.createFileIfNotExists();
        HashMap<Integer, TodoList> lists = new HashMap<>();
        try (
                FileInputStream inputStream = this.getContext().openFileInput(this.getFilePath());
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(streamReader)
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                JSONObject object = new JSONObject(line);
                TodoList list = new TodoList(
                        object.getInt("id"),
                        object.getString("title"),
                        object.getString("description")
                );
                lists.put(list.getId(), list);
            }
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
        }

        return lists;
    }

    public Integer getCurrentId() {
        Set<Integer> keys = this.getEntities().keySet();
        return Boolean.TRUE.equals(keys.isEmpty())
                ? 0
                : Collections.max(keys);
    }
}
