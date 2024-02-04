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
                    taskManager.markTaskAsDone(userInput);
                } else if (userInput.startsWith("unmark ")) {
                    taskManager.unmarkTaskAsNotDone(userInput);
                } else {
                    taskManager.addTask(userInput);
                }
            }
        }
    }
}
