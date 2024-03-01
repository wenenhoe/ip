package magus.task;

import magus.console.Console;
import magus.exception.ArgumentNotFoundException;
import magus.exception.UnknownArgumentException;
import magus.task.storage.Parser;
import magus.task.variant.Deadline;
import magus.task.variant.Event;
import magus.task.variant.Todo;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static magus.task.storage.FileIo.readTaskListFile;
import static magus.task.storage.FileIo.writeTaskListFile;

/**
 * Manages a collection of Task
 *
 * @see magus.task.Task
 */
public class TaskManager {
    private final List<Task> taskList;

    /**
     * Constructor for TaskManager that initialises an empty ArrayList
     * and imports task list from file system
     */
    public TaskManager() {
        this.taskList = new ArrayList<>();
        importTaskList();
    }

    /**
     * Prints all tasks in taskList
     */
    public void printAllTasks() {
        Console.printResponse("Here are the tasks in your list:");
        printTaskList(taskList);
    }

    /**
     * Adds a task to the taskList
     *
     * @param taskType Type of task
     * @param parser Console parser that parsed user input
     */
    public void addTask(TaskType taskType, magus.console.Parser parser) {
        Task task = null;

        switch (taskType) {
        case TODO:
            try {
                task = Todo.parseConsoleTaskInfo(parser);
            } catch (ArgumentNotFoundException | UnknownArgumentException e) {
                Console.printError(taskType.toString(), e);
            }
            break;
        case DEADLINE:
            try {
                task = Deadline.parseConsoleTaskInfo(parser);
            } catch (ArgumentNotFoundException | DateTimeParseException | UnknownArgumentException e) {
                Console.printError(taskType.toString(), e);
            }
            break;
        case EVENT:
            try {
                task = Event.parseConsoleTaskInfo(parser);
            } catch (ArgumentNotFoundException | DateTimeParseException | UnknownArgumentException e) {
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

    /**
     * Delete a task numbered with taskList using one-based indexing
     *
     * @param taskNum Task number based on taskList
     * @throws IndexOutOfBoundsException Unknown task number specified
     */
    public void deleteTask(int taskNum) throws IndexOutOfBoundsException {
        Task task = getTask(taskNum);
        taskList.remove(task);
        exportTaskList();
        Console.printResponse("Noted. I've removed this task:");
        Console.printResponse(task.toString(), 2);
        Console.printResponse("Now you have " + taskList.size() + " tasks in the list.");
    }

    /**
     * Mark a task done that is numbered with taskList using one-based indexing
     *
     * @param taskNum Task number based on taskList
     * @throws IndexOutOfBoundsException Unknown task number specified
     */
    public void markTaskAsDone(int taskNum) throws IndexOutOfBoundsException {
        Task task = getTask(taskNum);
        task.markAsDone();
        exportTaskList();
        Console.printResponse("Nice! I've marked this task as done:");
        Console.printResponse("  " + task);
    }

    /**
     * Unmark a task done that is numbered with taskList using one-based indexing
     *
     * @param taskNum Task number based on taskList
     * @throws IndexOutOfBoundsException Unknown task number specified
     */
    public void unmarkTaskAsDone(int taskNum) throws IndexOutOfBoundsException {
        Task task = getTask(taskNum);
        task.unmarkAsDone();
        exportTaskList();
        Console.printResponse("OK, I've marked this task as not done yet:");
        Console.printResponse("  " + task);
    }

    /**
     * Finds tasks that match the conditions specified in user input
     *
     * @param parser Console parser that parsed user input
     */
    public void findTasks(magus.console.Parser parser) {
        TaskFinder taskFinder = new TaskFinder(taskList);
        List<Task> filteredTaskList = taskFinder.filterTasks(parser);

        if (filteredTaskList != null) {
            Console.printResponse("Here are the matching tasks in your list:");
            printTaskList(filteredTaskList);
        }
    }

    private void printTaskList(List<Task> taskList) {
        int taskNum = 1;
        for (Task task: taskList) {
            Console.printResponse(taskNum + "." + task.toString());
            taskNum++;
        }
    }

    private Task getTask(int taskNum) throws IndexOutOfBoundsException {
        taskNum--; // Decrement to utilise as list index
        return taskList.get(taskNum);
    }

    private void importTaskList() {
        List<String> taskCommandStrings = readTaskListFile();
        for (String taskCommandString: taskCommandStrings) {
            Parser parser = new Parser(taskCommandString);
            TaskType taskType = parser.getTaskType();

            Task task = null;
            String[] taskInfoSplit = parser.getSplitTaskInfo();
            boolean isDone = parser.isDone();
            switch (taskType) {
            case TODO:
                String description = parser.getTaskInfo();
                task = Todo.parseStoredTaskInfo(description, isDone);
                break;
            case DEADLINE:
                task = Deadline.parseStoredTaskInfo(taskInfoSplit, isDone);
                break;
            case EVENT:
                task = Event.parseStoredTaskInfo(taskInfoSplit, isDone);
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
