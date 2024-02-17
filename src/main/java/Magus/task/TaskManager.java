package Magus.task;

import Magus.console.Console;
import Magus.exception.ArgumentNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static Magus.fileio.FileIo.writeTaskListFile;

public class TaskManager {
    private final List<Task> taskList;

    public TaskManager() {
        this.taskList = new ArrayList<>();
    }

    public void printTaskList() {
        int taskNum = 1;
        Console.printResponse("Here are the tasks in your list:");
        for (Task task: taskList) {
            Console.printResponse(taskNum + "." + task.toString());
            taskNum++;
        }
    }

    public void addTask(TaskType taskType, String taskInfo) {
        Task task = null;

        switch (taskType) {
        case TODO:
            try {
                task = Todo.parse(taskInfo);
            } catch (ArgumentNotFoundException e) {
                Console.printError(taskType.toString(), e);
            }
            break;
        case DEADLINE:
            try {
                task = Deadline.parse(taskInfo);
            } catch (ArgumentNotFoundException e) {
                Console.printError(taskType.toString(), e);
            }
            break;
        case EVENT:
            try {
                task = Event.parse(taskInfo);
            } catch (ArgumentNotFoundException e) {
                Console.printError(taskType.toString(), e);
            }
            break;
        }

        if (task == null) {
            return;
        }
        taskList.add(task);
        exportTaskList();
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
        exportTaskList();
        Console.printResponse("Nice! I've marked this task as done:");
        Console.printResponse("  " + task);
    }

    public void unmarkTaskAsDone(int taskNum) {
        Task task = getTask(taskNum);
        if (task == null) {
            return;
        }

        task.unmarkAsDone();
        exportTaskList();
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

    private void exportTaskList() {
        List<String> taskCommandStrings = new ArrayList<>();
        for (Task task: taskList) {
            String taskCommandString = task.toStoredString();
            taskCommandStrings.add(taskCommandString);
        }
        writeTaskListFile(taskCommandStrings);
    }
}
