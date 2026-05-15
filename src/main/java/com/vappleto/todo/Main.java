package com.vappleto.todo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class Main extends Application{
    private final TaskManager taskManager = new TaskManager();
    private TaskCategory selectedCategory;
    DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern(
                    "EEEE, MMMM d"
            );

    String today =
            LocalDate.now().format(formatter);

    @Override
    public void start(Stage stage) throws IOException {
        ListView<Task> taskListView = new ListView<>();
        taskManager.loadTasks();

        refreshAllTasks(taskListView);


        taskManager.removeOldCompletedTasks();

        taskManager.saveTasks();

        Label title = new Label("Today's focus -" + today);



        taskListView.setCellFactory(listView -> new ListCell<>() {

            @Override
            protected void updateItem(Task task,
                                      boolean empty) {

                super.updateItem(task, empty);

                if (empty || task == null) {

                    setText(null);
                    setStyle("");

                } else {

                    setText(task.toString());

                    if (task.isCompleted()) {

                        setStyle(
                                "-fx-text-fill: gray;" +
                                        "-fx-strikethrough: true;"
                        );

                    } else {

                        setStyle("");
                    }
                }
            }
        });


        Button addButton =
                new Button("Add task to list");

        Label title2 = new Label("Select a category");

        Button workButton = new Button("Work");
        Button schoolButton = new Button("School");
        Button selfCareButton = new Button("Self-Care");
        Button familyButton = new Button("Family");
        Button allTasksButton = new Button("View all Tasks");

        workButton.setOnAction(event -> {
            selectedCategory = TaskCategory.WORK;
            highlightSelectedButton(
                    workButton,
                    schoolButton,
                    selfCareButton,
                    familyButton,
                    allTasksButton
            );
            refreshTaskList(taskListView);

        });
        schoolButton.setOnAction(event -> {
            selectedCategory = TaskCategory.SCHOOL;
            highlightSelectedButton(
                    schoolButton,
                    workButton,
                    selfCareButton,
                    familyButton
            );
            refreshTaskList(taskListView);

        });
        selfCareButton.setOnAction(event -> {
            selectedCategory = TaskCategory.SELF_CARE;
            highlightSelectedButton(
                    selfCareButton,
                    workButton,
                    schoolButton,
                    familyButton
            );
            refreshTaskList(taskListView);

        });
        familyButton.setOnAction(event -> {
            selectedCategory = TaskCategory.FAMILY;
            highlightSelectedButton(
                    familyButton,
                    workButton,
                    schoolButton,
                    selfCareButton
            );
            refreshTaskList(taskListView);

        });



        TextField taskInput = new TextField();

        HBox categoryButtons = new HBox(10);

        categoryButtons.getChildren().addAll(workButton, schoolButton, selfCareButton, familyButton, allTasksButton);

        addButton.setOnAction(event -> {
                    String description = taskInput.getText();
            if (description.isBlank()
                    || selectedCategory == null) {

                return;
            }

            Task newTask =
                    taskManager.addTask(
                            selectedCategory,
                            description
                    );
            try {
                taskManager.saveTasks();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (selectedCategory == null) {

                refreshAllTasks(taskListView);

            } else {

                refreshTaskList(taskListView);
            }

            taskInput.clear();


                });
        Button completeButton = new Button("Mark Complete");
        completeButton.setOnAction(event -> {
            Task selectedTask = taskListView.getSelectionModel().getSelectedItem();

            if (selectedTask != null) {

                selectedTask.markCompleted();

                taskListView.refresh();

                refreshAllTasks(taskListView);

                try {

                    taskManager.saveTasks();

                } catch (IOException e) {

                    throw new RuntimeException(e);
                }
            }
        });

        Button removeButton = new Button("Remove Task");

        removeButton.setOnAction(event -> {
            Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                taskManager.removeTask(selectedTask);

                refreshTaskList(taskListView);

                try {
                    taskManager.saveTasks();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                taskManager.saveTasks();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        allTasksButton.setOnAction(event -> {
            selectedCategory = null;
            highlightSelectedButton(allTasksButton, workButton, schoolButton, selfCareButton, familyButton);
            refreshAllTasks(taskListView);
        });



        VBox root = new VBox(10);

        root.setPadding(new Insets(15));

        root.getChildren().addAll(title, taskInput, categoryButtons,  addButton, taskListView, completeButton, removeButton);

        Scene scene = new Scene(root, 500, 400);

        stage.setTitle("OpaList App");

        stage.setScene(scene);
        highlightSelectedButton(
                allTasksButton,
                workButton,
                schoolButton,
                selfCareButton,
                familyButton
        );

        refreshAllTasks(taskListView);

        stage.show();


    }
    private void highlightSelectedButton(
            Button selected, Button... others) {

        selected.setStyle(
                "-fx-background-color: #7aa6ff;" +
                        "-fx-text-fill: white;"
        );

        for (Button button : others) {

            button.setStyle("");
        }
    }

    private void refreshTaskList(ListView<Task> taskListView) {
        taskListView.getItems().clear();

        if (selectedCategory != null) {
            taskListView.getItems().addAll(taskManager.getTaskByCategory(selectedCategory));
        }
    }

    private void refreshAllTasks(ListView<Task> taskListView) {
        taskListView.getItems().clear();
        taskListView.getItems().addAll(taskManager.getAllTasks());
    }
    public static void main(String[] args) {
        launch();


    }

}
