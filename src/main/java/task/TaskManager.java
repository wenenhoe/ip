package task;

import console.Console;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private final List<Task> taskList;

    public TaskManager() {
        this.taskList = new ArrayList<>();
    }

    public void printTaskList() {
        int taskNum = 1;
        Console.printResponse("Here are the tasks in your list:");
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

    public void addTask(TaskType taskType, String taskInfo) {
    }

    public void markTaskAsDone(int taskNum) {
        Task task = getTask(taskNum);
        if (task == null) {
            return;
        }

        task.markAsDone();
        Console.printResponse("Nice! I've marked this task as done:");
        Console.printResponse("  " + task.toString());
    }

    public void unmarkTaskAsNotDone(int taskNum) {
        Task task = getTask(taskNum);
        if (task == null) {
            return;
        }

        task.unmarkAsNotDone();
        Console.printResponse("OK, I've marked this task as not done yet:");
        Console.printResponse("  " + task.toString());
    }

    private Task getTask(int taskNum) {
        taskNum--; // Decrement to utilise as list index

        try {
            return taskList.get(taskNum);
        } catch (IndexOutOfBoundsException ok) {
            return null; // Outside range of task list, unable to modify task status
        }
    }
}
