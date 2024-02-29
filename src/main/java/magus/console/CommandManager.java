package magus.console;

import magus.exception.CommandNotFoundException;
import magus.exception.UnknownArgumentException;
import magus.task.TaskManager;
import magus.task.TaskType;

public class CommandManager {
    public static void processInput(Parser parser, TaskManager taskManager)
            throws CommandNotFoundException, UnknownArgumentException {
        int taskNum;
        String errorContext;
        Command command = parser.getCommand();
        String additionalInput = parser.getAdditionalInput();

        switch (command) {
        case UNKNOWN:
            errorContext = String.format("Unknown command in \"%s\"", parser.getCommandCandidate());
            throw new CommandNotFoundException(errorContext);
        case LIST:
            taskManager.printAllTasks();
            break;
        case FIND:
            taskManager.findTasks(parser);
            break;
        case BYE:
            Console.printResponse("Bye. Hope to see you again soon!");
            break;
        case MARK:
            taskNum = Parser.parseInt(additionalInput);
            try {
                taskManager.markTaskAsDone(taskNum);
            } catch (IndexOutOfBoundsException ignored) {
                errorContext = String.format("Unknown argument in \"%s\"", additionalInput);
                throw new UnknownArgumentException(errorContext);
            }
            break;
        case UNMARK:
            taskNum = Parser.parseInt(additionalInput);
            try {
                taskManager.unmarkTaskAsDone(taskNum);
            } catch (IndexOutOfBoundsException ignored) {
                errorContext = String.format("Unknown argument in \"%s\"", additionalInput);
                throw new UnknownArgumentException(errorContext);
            }
            break;
        case DELETE:
            taskNum = Parser.parseInt(additionalInput);
            try {
                taskManager.deleteTask(taskNum);
            } catch (IndexOutOfBoundsException ignored) {
                errorContext = String.format("Unknown argument in \"%s\"", additionalInput);
                throw new UnknownArgumentException(errorContext);
            }
            break;
        case TODO:
            taskManager.addTask(TaskType.TODO, parser);
            break;
        case DEADLINE:
            taskManager.addTask(TaskType.DEADLINE, parser);
            break;
        case EVENT:
            taskManager.addTask(TaskType.EVENT, parser);
            break;
        }
    }
}
