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

    public void markTaskAsDone(int taskNum) {
        Task task;
        taskNum--; // Decrement to utilise as list index

        try {
            task = taskList.get(taskNum);
        } catch (IndexOutOfBoundsException ignored) {
            return; // Outside range of task list, unable to modify task status
        }

        task.markAsDone();
        Console.printResponse("Nice! I've marked this task as done:");
        Console.printResponse("  " + task.toString());
    }

    public void unmarkTaskAsNotDone(int taskNum) {
        Task task;
        taskNum--; // Decrement to utilise as list index

        try {
            task = taskList.get(taskNum);
        } catch (IndexOutOfBoundsException ignored) {
            return; // Outside range of task list, unable to modify task status
        }

        task.unmarkAsNotDone();
        Console.printResponse("OK, I've marked this task as not done yet:");
        Console.printResponse("  " + task.toString());
    }
}
