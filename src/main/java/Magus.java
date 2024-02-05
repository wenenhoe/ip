import console.Console;
import console.Command;
import console.Parser;
import task.TaskManager;
import task.TaskType;

public class Magus {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Console.printWelcomeMessage();

        while (true) {
            String input = Console.getUserInput();
            Parser parser = new Parser(input);
            boolean isContinue = Magus.processInput(parser, taskManager);

            if (!isContinue) {
                break;
            }
        }
    }

    public static boolean processInput(Parser parser, TaskManager taskManager) {
        int taskNum;
        String input = parser.getInput();
        Command command = parser.getCommand();
        String additionalInput = parser.getAdditionalInput();
        boolean isSingleWord = parser.isSingleWord();

        switch (command) {
        case DEFAULT:
            taskManager.addTask(input);
            break;
        case LIST:
            if (isSingleWord) {
                taskManager.printTaskList();
            } else {
                taskManager.addTask(input);
            }
            break;
        case BYE:
            if (isSingleWord) {
                Console.printResponse("Bye. Hope to see you again soon!");
                return false;
            }
            taskManager.addTask(input);
            break;
        case MARK:
            taskNum = Parser.parseInt(additionalInput);
            taskManager.markTaskAsDone(taskNum);
            break;
        case UNMARK:
            taskNum = Parser.parseInt(additionalInput);
            taskManager.unmarkTaskAsNotDone(taskNum);
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
        return true;
    }
}
