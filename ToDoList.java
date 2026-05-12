import java.util.*;

public class ToDoList {

    private static Scanner userInput = new Scanner(System.in);
    private static Map<TaskCategory, List<Task>> tasks = new HashMap<>();

    public enum TaskCategory {
        WORK, SCHOOL, SELF_CARE, FAMILY
    }

    static class Task {
        String description;
        boolean isCompleted;

        Task(String description) {
            this.description = description;
            this.isCompleted = false;
        }

        void markComplete() {
            this.isCompleted = true;
        }

        @Override
        public String toString () {
            return (isCompleted ? "[✔]" : "[ ]") + description;
        }
    }

        private static void addTask(TaskCategory category, String task) {
            tasks.computeIfAbsent(category, k -> new ArrayList<>()).add(new Task(task));
        }

    private static void displayTasks() {
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
    private static void markTaskComplete(TaskCategory category, int index) {
        List<Task> taskList = tasks.get(category);
        if (taskList != null && index >0 && index <= taskList.size()) {
            taskList.get(index -1).markComplete();
            System.out.println("Task marked as complete!");
        } else {
            System.out.println("Invalid task number");
        }

    }

    private static void removeTask(TaskCategory category, int index) {
        List<Task> taskList = tasks.get(category);
        if (taskList != null && index >0 && index <= taskList.size()){
            taskList.remove(index -1);
            System.out.println("Task rmeoved");
        }else {
            System.out.println("Invalid task number");
        }
    }
        

    public static void main(String[] args) {
        System.out.println("Welcome to your task manager!");

        try {

            while (true) {
                System.out.println("Choose an action:");
                System.out.println("1. Add Task\n2. View Tasks\n3. Mark Task as Complete\n4. Remove Task\n5. Exit");
                String choice = userInput.nextLine();

                switch (choice) {
                    case "1":
                        System.out.println("Select a category: 1. Work 2. School 3. Self-Care 4. Family");
                        TaskCategory category = getCategory();
                        if (category == null) continue;

                        System.out.println("Enter Task Description:");
                        String taskDescription = userInput.nextLine();
                        addTask(category, taskDescription);
                        System.out.println("Task Added!");
                        break;

                    case "2":
                        displayTasks();
                        break;

                    case "3":
                        System.out.println("Selece category: 1. Work 2. School 3. Self-Care 4. Family\"");
                        category = getCategory();
                        if (category == null) continue;
                        displayTasks();
                        System.out.println("Enter task number to mark as complete:");
                        int completeIndex = Integer.parseInt(userInput.nextLine());
                        markTaskComplete(category, completeIndex);
                        break;

                    case "4":
                        System.out.println("Select category: 1. Work 2. School 3. Self-Care 4. Family\"");
                        category = getCategory();
                        if (category == null) continue;
                        displayTasks();
                        System.out.println("Enter task number to remove:");
                        int removeIndex = Integer.parseInt(userInput.nextLine());
                        removeTask(category,removeIndex);
                        break;

                    case "5":
                        System.out.println("Goofbye!");
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

            case "1": return TaskCategory.WORK;
            case "2": return TaskCategory.SCHOOL;
            case "3": return TaskCategory.SELF_CARE;
            case "4": return TaskCategory.FAMILY;
            default:
                System.out.println("Invalid category. Please choose a valid option");
                return null;

        }
    }

}
