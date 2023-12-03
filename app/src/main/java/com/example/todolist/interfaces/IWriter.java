package com.example.todolist.interfaces;

import android.content.Context;

import org.json.JSONException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public interface IWriter<K, E> {
    Context getContext();

    String getFilePath();

    String convertEntityToJson(E entity) throws JSONException;

    void saveEntities(Map<K, E> entities);

    default Boolean fileExists() {
        return this.getContext().getFileStreamPath(this.getFilePath()).exists();
    }

    default void createFileIfNotExists() {
        if (Boolean.FALSE.equals(this.fileExists())) {
            try (FileOutputStream outputStream = this.getContext().openFileOutput(this.getFilePath(), Context.MODE_PRIVATE)) {
                outputStream.write("{}".getBytes(StandardCharsets.UTF_8));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
