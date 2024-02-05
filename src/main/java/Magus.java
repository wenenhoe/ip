import console.Console;
import task.TaskManager;
import task.TaskType;

public class Magus {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Console.printWelcomeMessage();

        while (true) {
            String input = Console.getUserInput();
            boolean isContinue = Magus.processInput(input, taskManager);

            if (!isContinue) {
                break;
            }
        }
    }

    public static boolean processInput(String input, TaskManager taskManager) {
        int taskNum;
        Command command = getCommand(input);
        String additionalInput = getAdditionalInput(input, command);
        boolean isSingleWord = additionalInput.isEmpty();

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
            taskNum = parseInt(additionalInput);
            taskManager.markTaskAsDone(taskNum);
            break;
        case UNMARK:
            taskNum = parseInt(additionalInput);
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

    public static Command getCommand(String input) {
        String firstWord = input;

        int firstSpaceIndex = input.indexOf(" ");
        if (firstSpaceIndex != -1) {
            firstWord = input.substring(0, firstSpaceIndex);
        }
        return Command.getEnum(firstWord);
    }

    public static String getAdditionalInput(String input, Command command) {
        String additionalInput = "";
        int additionalInputIndex = command.toString().length() + 1;

        boolean isNotDefaultCommand = command != Command.DEFAULT;
        boolean isAdditionalInputExists = additionalInputIndex < input.length();

        if (isNotDefaultCommand && isAdditionalInputExists) {
            // if it is non-DEFAULT commands and there is additional input
            additionalInput = input.substring(additionalInputIndex);
        }

        return additionalInput;
    }

    public static int parseInt(String candidate) {
        try {
            return Integer.parseInt(candidate);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
