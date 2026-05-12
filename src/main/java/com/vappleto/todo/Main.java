package com.vappleto.todo;

import java.util.Scanner;

public class Main {

    private static final Scanner userInput = new Scanner(System.in);
    private static final TaskManager taskManager = new TaskManager();

    public static void main(String[] args) {


        System.out.println("To-Do App Started");

        try {

            while (true) {
                System.out.println("Choose an action:");
                System.out.println("1. Add Task\n" +
                                   "2. View Tasks\n" +
                                   "3. Mark Task as Complete\n" +
                                   "4. Remove Task\n" +
                                   "5. Exit");

                String choice = userInput.nextLine();

                switch (choice) {
                    case "1":
                        System.out.println("Select a category:\n" +
                                "1. Work\n" +
                                "2. School\n" +
                                "3. Self-Care" +
                                "4. Family");

                        TaskCategory category = getCategory();

                        if (category == null) continue;

                        System.out.println("Enter Task Description:");

                        String taskDescription = userInput.nextLine();
                        taskManager.addTask(category, taskDescription);
                        System.out.println("Task Added!");
                        break;

                    case "2":
                        taskManager.displayTasks();
                        break;

                    case "3":
                        System.out.println("Select category:\n" +
                                "1. Work\n" +
                                "2. School\n" +
                                "3. Self-Care\n" +
                                "4. Family\n");

                        category = getCategory();
                        if (category == null) continue;
                        taskManager.displayTasks();

                        System.out.println("Enter task number to mark as complete:");
                        int completeIndex = Integer.parseInt(userInput.nextLine());
                        taskManager.markTaskComplete(category, completeIndex);
                        break;

                    case "4":
                        System.out.println("Select a category:\n" +
                                "1. Work\n" +
                                "2. School\n" +
                                "3. Self-Care" +
                                "4. Family");
                        category = getCategory();
                        if (category == null) continue;
                        taskManager.displayTasks();
                        System.out.println("Enter task number to remove:");
                        int removeIndex = Integer.parseInt(userInput.nextLine());
                        taskManager.removeTask(category,removeIndex);
                        break;

                    case "5":
                        System.out.println("Goodbye!");
                        userInput.close();
                        return;

                    default:
                        System.out.println("Invalid category. Please choose a valid option.");
                }

            }
        } finally {
            userInput.close();
        }
    }

    private static TaskCategory getCategory() {
        String categoryInput = userInput.nextLine();

        switch (categoryInput) {
            case "1":
                return TaskCategory.WORK;

            case "2":
                return TaskCategory.SCHOOL;

            case "3":
                return TaskCategory.SELF_CARE;

            case "4":
                return TaskCategory.FAMILY;

            default:
                System.out.println("Invalid category. Please choose a valid option.");

            return null;

        }
    }
}
