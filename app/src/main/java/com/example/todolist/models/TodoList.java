package com.example.todolist.models;

import java.util.Objects;

public class TodoList {
    private Integer id;
    private String title;
    private String description;

    public TodoList() {
    }

    public TodoList(Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoList todoList = (TodoList) o;
        return Objects.equals(id, todoList.id) && Objects.equals(title, todoList.title) && Objects.equals(description, todoList.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description);
    }
}
