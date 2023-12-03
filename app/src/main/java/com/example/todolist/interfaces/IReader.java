package com.example.todolist.interfaces;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public interface IReader<K, E> {
    Context getContext();

    String getFilePath();

    Map<K, E> getEntities();

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
