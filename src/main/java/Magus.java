public class Magus {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Console.printWelcomeMessage();

        while (true) {
            String userInput = Console.getUserInput();

            switch (userInput) {
            case "list":
                taskManager.printTaskList();
                break;
            case "bye":
                Console.printResponse("Bye. Hope to see you again soon!");
                return;
            default:
                if (userInput.startsWith("mark ")) {
                    String parsedUserInput = parseUserInput(userInput, "mark ");
                    int taskNum = parseInt(parsedUserInput);
                    taskManager.markTaskAsDone(taskNum);
                } else if (userInput.startsWith("unmark ")) {
                    String parsedUserInput = parseUserInput(userInput, "unmark ");
                    int taskNum = parseInt(parsedUserInput);
                    taskManager.unmarkTaskAsNotDone(taskNum);
                } else {
                    taskManager.addTask(userInput);
                }
            }
        }
    }

    public static String parseUserInput(String userInput, String actionType) {
        return userInput.replace(actionType, "");
    }

    public static int parseInt(String candidate) {
        try {
            return Integer.parseInt(candidate);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
