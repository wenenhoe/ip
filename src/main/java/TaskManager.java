import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private final List<Task> taskList;

    public TaskManager() {
        this.taskList = new ArrayList<>();
    }

    public void printTaskList() {
        int taskNum = 1;
        System.out.println("\tHere are the tasks in your list:");
        for (Task task : taskList) {
            Console.printResponse(taskNum + "." + task.toString());
            taskNum++;
        }
    }

    public void addTask(String description) {
        Task task = new Task(description);
        taskList.add(task);
        Console.printResponse("added: " + description);
    }

    public void markTaskAsDone(String userInput) {
        int taskNum = TaskManager.parseUserInput(userInput, "mark ");
        if (taskNum <= 0) {
            return;
        }

        try {
            Task task = taskList.get(taskNum - 1);
            task.markAsDone();
            Console.printResponse("Nice! I've marked this task as done:");
            Console.printResponse("  " + task.toString());
        } catch (IndexOutOfBoundsException ignored) {
            // Outside range of task list, unable to modify task status
        }
    }

    public void unmarkTaskAsNotDone(String userInput) {
        int taskNum = TaskManager.parseUserInput(userInput, "unmark ");
        if (taskNum <= 0) {
            return;
        }

        try {
            Task task = taskList.get(taskNum - 1);
            task.unmarkAsNotDone();
            Console.printResponse("OK, I've marked this task as not done yet:");
            Console.printResponse("  " + task.toString());
        } catch (IndexOutOfBoundsException ignored) {
            // Outside range of task list, unable to modify task status
        }
    }

    private static int parseUserInput(String userInput, String actionType) {
        userInput = userInput.replace(actionType, "");
        try {
            return Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
