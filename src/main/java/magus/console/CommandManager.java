package magus.console;

import magus.exception.CommandNotFoundException;
import magus.task.TaskManager;
import magus.task.TaskType;

public class CommandManager {
    public static void processInput(Parser parser, TaskManager taskManager)
            throws CommandNotFoundException {
        int taskNum;
        Command command = parser.getCommand();
        String additionalInput = parser.getAdditionalInput();

        switch (command) {
        case UNKNOWN:
            throw new CommandNotFoundException();
        case LIST:
            taskManager.printAllTasks();
            break;
        case FIND:
            taskManager.findTasks(additionalInput);
            break;
        case BYE:
            Console.printResponse("Bye. Hope to see you again soon!");
            break;
        case MARK:
            taskNum = Parser.parseInt(additionalInput);
            taskManager.markTaskAsDone(taskNum);
            break;
        case UNMARK:
            taskNum = Parser.parseInt(additionalInput);
            taskManager.unmarkTaskAsDone(taskNum);
            break;
        case DELETE:
            taskNum = Parser.parseInt(additionalInput);
            taskManager.deleteTask(taskNum);
            break;
        case TODO:
            taskManager.addTask(TaskType.TODO, additionalInput);
            break;
        case DEADLINE:
            taskManager.addTask(TaskType.DEADLINE, additionalInput);
            break;
        case EVENT:
            taskManager.addTask(TaskType.EVENT, additionalInput);
            break;
        }
    }
}
