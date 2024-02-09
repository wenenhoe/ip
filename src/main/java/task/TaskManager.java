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

    @Deprecated
    public void addTask(String description) {
        Task task = Task.parse(description);
        taskList.add(task);
        Console.printResponse("added: " + description);
    }

    public void addTask(TaskType taskType, String taskInfo) {
        Task task = null;

        switch (taskType) {
        case DEFAULT:
            int tabCount = 1;
            Console.printWarning("This task type has been deprecated", tabCount);
            break;
        case TODO:
            task = Todo.parse(taskInfo);
            break;
        case DEADLINE:
            task = Deadline.parse(taskInfo);
            break;
        case EVENT:
            task = Event.parse(taskInfo);
            break;
        }

        if (task == null) {
            return;
        }
        taskList.add(task);
        Console.printResponse("Got it. I've added this task:");
        Console.printResponse(task.toString(), 2);
        Console.printResponse("Now you have " + taskList.size() + " tasks in the list.");
    }

    public void markTaskAsDone(int taskNum) {
        Task task = getTask(taskNum);
        if (task == null) {
            return;
        }

        task.markAsDone();
        Console.printResponse("Nice! I've marked this task as done:");
        Console.printResponse("  " + task);
    }

    public void unmarkTaskAsDone(int taskNum) {
        Task task = getTask(taskNum);
        if (task == null) {
            return;
        }

        task.unmarkAsDone();
        Console.printResponse("OK, I've marked this task as not done yet:");
        Console.printResponse("  " + task);
    }

    private Task getTask(int taskNum) {
        taskNum--; // Decrement to utilise as list index

        try {
            return taskList.get(taskNum);
        } catch (IndexOutOfBoundsException ok) {
            return null; // Outside range of task list
        }
    }
}
