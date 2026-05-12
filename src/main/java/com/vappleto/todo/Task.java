package com.vappleto.todo;

public class Task {

    private String description;
    private boolean completed;

    public Task(String description) {
        this.description = description;
        this.completed = false;
    }
    public void markCompleted() {
        completed = true;
    }
    public boolean isCompleted() {
        return completed;
    }
    public String getDescription() {
        return description;
    }
    @Override
    public String toString() {
        return (completed ? "[✔] " : "[ ] ") + description;
    }
}
