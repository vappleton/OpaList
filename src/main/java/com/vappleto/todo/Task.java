package com.vappleto.todo;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Task {

    private String description;
    private boolean completed;
    private TaskCategory category;
    private LocalDate createdDate;

    public Task(String description, TaskCategory category, LocalDate createdDate) {
        this.description = description;
        this.completed = false;
        this.category = category;
        this.createdDate = LocalDate.now();
    }
    public LocalDate getCreatedDate() {
        return createdDate;
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
