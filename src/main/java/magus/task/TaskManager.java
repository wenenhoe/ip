package magus.task;

import magus.console.Console;
import magus.exception.ArgumentNotFoundException;
import magus.task.storage.Parser;
import magus.task.variant.Deadline;
import magus.task.variant.Event;
import magus.task.variant.Todo;

import java.util.ArrayList;
import java.util.List;

import static magus.task.storage.FileIo.readTaskListFile;
import static magus.task.storage.FileIo.writeTaskListFile;

public class TaskManager {
    private final List<Task> taskList;

    public TaskManager() {
        this.taskList = new ArrayList<>();
        importTaskList();
    }

    public void printTaskList() {
        int taskNum = 1;
        Console.printResponse("Here are the tasks in your list:");
        for (Task task: taskList) {
            Console.printResponse(taskNum + "." + task.toString());
            taskNum++;
        }
    }

    public void addTask(TaskType taskType, magus.console.Parser parser) {
        Task task = null;

        switch (taskType) {
        case TODO:
            try {
                task = Todo.parseConsoleTaskInfo(parser);
            } catch (ArgumentNotFoundException e) {
                Console.printError(taskType.toString(), e);
            }
            break;
        case DEADLINE:
            try {
                task = Deadline.parseConsoleTaskInfo(parser);
            } catch (ArgumentNotFoundException e) {
                Console.printError(taskType.toString(), e);
            }
            break;
        case EVENT:
            try {
                task = Event.parseConsoleTaskInfo(parser);
            } catch (ArgumentNotFoundException e) {
                Console.printError(taskType.toString(), e);
            }
            break;
        }

        if (task == null) {
            return; // Task not created
        }
        taskList.add(task);
        exportTaskList();
        Console.printResponse("Got it. I've added this task:");
        Console.printResponse(task.toString(), 2);
        Console.printResponse("Now you have " + taskList.size() + " tasks in the list.");
    }

    public void deleteTask(int taskNum) {
        Task task = getTask(taskNum);
        if (task == null) {
            return;
        }

        taskList.remove(task);
        exportTaskList();
        Console.printResponse("Noted. I've removed this task:");
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

    private void importTaskList() {
        List<String> taskCommandStrings = readTaskListFile();
        for (String taskCommandString: taskCommandStrings) {
            Parser parser = new Parser(taskCommandString);
            TaskType taskType = parser.getTaskType();

            Task task = null;
            switch (taskType) {
            case TODO:
                task = Todo.parseStoredTaskInfo(parser);
                break;
            case DEADLINE:
                task = Deadline.parseStoredTaskInfo(parser);
                break;
            case EVENT:
                task = Event.parseStoredTaskInfo(parser);
                break;
            }

            if (task == null) {
                return; // Task not created
            }
            taskList.add(task);
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
