package com.example.todolist.models;

import java.util.Objects;

public class Todo {
    private Integer id;
    private String title;
    private String description;
    private Boolean finished;
    private Boolean priority;
    private Integer todoList;

    public Todo() {
    }

    public Todo(Integer id, String title, String description, Boolean finished, Boolean priority, Integer todoList) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.finished = finished;
        this.priority = priority;
        this.todoList = todoList;
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

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Boolean getPriority() {
        return priority;
    }

    public void setPriority(Boolean priority) {
        this.priority = priority;
    }

    public Integer getTodoList() {
        return todoList;
    }

    public void setTodoList(Integer todoList) {
        this.todoList = todoList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return Objects.equals(id, todo.id) && Objects.equals(title, todo.title) && Objects.equals(description, todo.description) && Objects.equals(finished, todo.finished) && Objects.equals(priority, todo.priority) && Objects.equals(todoList, todo.todoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, finished, priority, todoList);
    }
}
