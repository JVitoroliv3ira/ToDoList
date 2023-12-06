package com.example.todolist.writers;

import android.content.Context;

import com.example.todolist.interfaces.IWriter;
import com.example.todolist.models.Todo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

public class TodoWriter implements IWriter<Integer, Todo> {

    private final Context context;

    public TodoWriter(Context context) {
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
    public String convertEntityToJson(Todo entity) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("id", entity.getId());
        object.put("title", entity.getTitle());
        object.put("description", entity.getDescription());
        object.put("finished", entity.getFinished());
        object.put("priority", entity.getPriority());
        object.put("todoList", entity.getTodoList());
        return object.toString();
    }

    @Override
    public void saveEntities(Map<Integer, Todo> entities) {
        this.createFileIfNotExists();
        try (
                OutputStreamWriter streamWriter = new OutputStreamWriter(this.getContext().openFileOutput(this.getFilePath(), Context.MODE_PRIVATE));
                BufferedWriter writer = new BufferedWriter(streamWriter)
        ) {
            for (Map.Entry<Integer, Todo> entry : entities.entrySet()) {
                String data = this.convertEntityToJson(entry.getValue());
                writer.write(data);
                writer.newLine();
            }
            writer.flush();
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
        }
    }
}
