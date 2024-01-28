import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private final List<Task> taskList;

    public TaskManager() {
        this.taskList = new ArrayList<>();
    }

    public void printTaskList() {
        Task[] tasks = taskList.toArray(new Task[0]);

        int taskNum = 1;
        System.out.println("\tHere are the tasks in your list:");
        for (Task task : tasks) {
            System.out.println("\t" + taskNum++ + "." + task.toString());
        }
    }

    public void addTask(String description) {
        Task t = new Task(description);
        taskList.add(t);
        System.out.println("\tadded: " + description);
    }

    public void markTaskAsDone(String userInput) {
        int taskNum = parseUserInput(userInput, "mark ");
        Task t = taskList.get(taskNum - 1);
        t.markAsDone();
        System.out.println("\tNice! I've marked this task as done:");
        System.out.println("\t  " + t.toString());
    }

    public void unmarkTaskAsNotDone(String userInput) {
        int taskNum = parseUserInput(userInput, "unmark ");
        Task t = taskList.get(taskNum - 1);
        t.unmarkAsNotDone();
        System.out.println("\tOK, I've marked this task as not done yet:");
        System.out.println("\t  " + t.toString());
    }

    private int parseUserInput(String userInput, String actionType) {
        userInput = userInput.replace(actionType, "");
        return Integer.parseInt(userInput);
    }
}
