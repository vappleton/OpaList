package com.vappleto.todo;

import java.util.*;

public class TaskManager {

    private final Map<TaskCategory, List<Task>> tasks = new HashMap<>();

    public void addTask(TaskCategory category, String description) {
        tasks.computeIfAbsent(category, k-> new ArrayList<>())
                .add(new Task(description));
    }
    public Map<TaskCategory, List<Task>> getTasks() {
        return tasks;
    }

    public void displayTasks() {
        for (TaskCategory category : TaskCategory.values()) {
            System.out.println(category + " Tasks:");
            List<Task> taskList = tasks.getOrDefault(category, new ArrayList<>());
            if (taskList.isEmpty()) {
                System.out.println("No tasks in this category.");
            } else {
                for (int i = 0; i < taskList.size(); i++) {
                    System.out.println((i + 1) + ". " + taskList.get(i));
                }
            }
            System.out.println();
        }
    }

    public void markTaskComplete(TaskCategory category, int index) {
        List<Task> taskList = tasks.get(category);
        if (taskList != null && index >0 && index <= taskList.size()) {
            taskList.get(index -1).markCompleted();
            System.out.println("Task marked as complete!");
        } else {
            System.out.println("Invalid task number");
        }

    }
    public void removeTask(TaskCategory category, int index) {
        List<Task> taskList = tasks.get(category);
        if (taskList != null && index >0 && index <= taskList.size()){
            taskList.remove(index -1);
            System.out.println("Task rmeoved");
        }else {
            System.out.println("Invalid task number");
        }
    }

}
