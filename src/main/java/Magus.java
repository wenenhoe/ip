import console.Console;
import console.Command;
import console.Parser;
import task.TaskManager;
import task.TaskType;

public class Magus {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Console.printWelcomeMessage();
        boolean isExitProgram = false;

        while (!isExitProgram) {
            String input = Console.getUserInput();
            Parser parser = new Parser(input);
            Magus.processInput(parser, taskManager);

            // Exit when bye command issued
            isExitProgram = parser.getCommand() == Command.BYE;
        }
    }

    public static void processInput(Parser parser, TaskManager taskManager) {
        int taskNum;
        Command command = parser.getCommand();
        String additionalInput = parser.getAdditionalInput();

        switch (command) {
        case UNKNOWN:
            // TODO: Error, no such command
            break;
        case LIST:
            taskManager.printTaskList();
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
