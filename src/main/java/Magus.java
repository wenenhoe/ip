import console.Console;
import task.TaskManager;
import task.TaskType;

public class Magus {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Console.printWelcomeMessage();

        while (true) {
            String userInput = Console.getUserInput();

            int taskNum;
            String firstWord, restOfInput;
            boolean isSingleWord = false;
            int firstSpaceIndex = userInput.indexOf(" ");
            if (firstSpaceIndex == -1) {
                firstWord = userInput;
                restOfInput = "";
                isSingleWord = true;
            } else {
                firstWord = userInput.substring(0, firstSpaceIndex);
                restOfInput = userInput.substring(firstSpaceIndex + 1);
            }
            Command command = Command.getEnum(firstWord);

            switch (command) {
            case DEFAULT:
                taskManager.addTask(userInput);
                break;
            case LIST:
                if (isSingleWord) {
                    taskManager.printTaskList();
                } else {
                    taskManager.addTask(userInput);
                }
                break;
            case BYE:
                if (isSingleWord) {
                    Console.printResponse("Bye. Hope to see you again soon!");
                    return;
                }
                taskManager.addTask(userInput);
                break;
            case MARK:
                taskNum = parseInt(restOfInput);
                taskManager.markTaskAsDone(taskNum);
                break;
            case UNMARK:
                taskNum = parseInt(restOfInput);
                taskManager.unmarkTaskAsNotDone(taskNum);
                break;
            case TODO:
                taskManager.addTask(TaskType.TODO, restOfInput);
                break;
            case DEADLINE:
                taskManager.addTask(TaskType.DEADLINE, restOfInput);
                break;
            case EVENT:
                taskManager.addTask(TaskType.EVENT, restOfInput);
                break;
            }
        }
    }

    public static int parseInt(String candidate) {
        try {
            return Integer.parseInt(candidate);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
