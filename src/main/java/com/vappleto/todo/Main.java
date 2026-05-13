package com.vappleto.todo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application{
    private final TaskManager taskManager = new TaskManager();

    @Override
    public void start(Stage stage) {
        Label title = new Label("My To-Do List");
        ListView<Task> taskListView = new ListView<>();
        TextField taskInput = new TextField();
        taskInput.setPromptText("Enter Task");
        ComboBox<TaskCategory> categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll(TaskCategory.values());
        categoryBox.setValue(TaskCategory.WORK);
        Button addButton =
                new Button("Add Task");

        addButton.setOnAction(event -> {
                    String description = taskInput.getText();
                    TaskCategory category = categoryBox.getValue();

                    if (!description.isBlank()) {

                        taskManager.addTask(category, description);

                        Task newTask = taskManager.addTask(category, description);

                        taskListView.getItems().add(newTask);

                        taskInput.clear();
                    }
                });
        Button completeButton = new Button("Mark Complete");
        completeButton.setOnAction(event -> {
            Task selectedTask = taskListView.getSelectionModel().getSelectedItem();

            if (selectedTask != null) {
                selectedTask.markCompleted();
                taskListView.refresh();
            }
        });

        Button removeButton = new Button("Remove Task");

        removeButton.setOnAction(event -> {
            Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                taskListView.getItems().remove(selectedTask);
            }
        });



        VBox root = new VBox(10);

        root.setPadding(new Insets(15));

        root.getChildren().addAll(title, categoryBox, taskInput, addButton, taskListView, completeButton, removeButton);

        Scene scene = new Scene(root, 500, 400);

        stage.setTitle("To-Do App");

        stage.setScene(scene);

        stage.show();


    }
    public static void main(String[] args) {
        launch();


    }
}
