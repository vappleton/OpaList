package com.vappleto.todo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;

import java.time.LocalDate;
import java.util.*;

public class TaskManager {

    private  Map<TaskCategory, List<Task>> tasks = new HashMap<>();

    public Task addTask(TaskCategory category, String description) {
        Task task = new Task(description, category, LocalDate.now());

        tasks.computeIfAbsent(category, k -> new ArrayList<>()).add(task);

        return task;
    }
    public Map<TaskCategory, List<Task>> getTasks() {
        return tasks;
    }


    public List<Task> getTaskByCategory(TaskCategory category) {
        return tasks.getOrDefault( category, new ArrayList());
    }
    public List<Task> getAllTasks() {
        List<Task> allTasks = new ArrayList<>();
        for (List<Task> taskList : tasks.values()) {
            allTasks.addAll(taskList);
        }
        allTasks.sort((task1, task2) -> {

            if (task1.isCompleted()
                    == task2.isCompleted()) {

                return 0;
            }

            return task1.isCompleted() ? 1 : -1;
        });
        return allTasks;
    }
    public void removeOldCompletedTasks() {

        LocalDate today = LocalDate.now();

        for (List<Task> taskList : tasks.values()) {

            taskList.removeIf(task ->

                    task.isCompleted()
                            &&
                            !task.getCreatedDate().equals(today)
            );
        }
    }
    public void saveTasks() throws IOException {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).setPrettyPrinting().create();

        try (Writer writer = new FileWriter("tasks.json")) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTasks() {
        File file = new File("tasks.json");

        if (!file.exists()) {

            return;
        }

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

        try (Reader reader =
                     new FileReader(file)) {

            Type type =
                    new TypeToken<
                            Map<TaskCategory,
                                    List<Task>>>() {}.getType();

            tasks = gson.fromJson(reader, type);

            if (tasks == null) {

                tasks = new HashMap<>();
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
    public void removeTask(Task task) {

        for (List<Task> taskList : tasks.values()) {

            taskList.remove(task);
        }
    }


}
