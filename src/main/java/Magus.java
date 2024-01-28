import java.util.Scanner;

public class Magus {
    public static void main(String[] args) {
        String separator = "-----------------------------------------------";
        String logo =
                "  __  __               _____   _    _    _____ \n" +
                " |  \\/  |     /\\      / ____| | |  | |  / ____|\n" +
                " | \\  / |    /  \\    | |  __  | |  | | | (___  \n" +
                " | |\\/| |   / /\\ \\   | | |_ | | |  | |  \\___ \\ \n" +
                " | |  | |  / ____ \\  | |__| | | |__| |  ____) |\n" +
                " |_|  |_| /_/    \\_\\  \\_____|  \\____/  |_____/ \n";
        System.out.println(logo);
        System.out.println("Hello I'm Magus");
        System.out.println("What can I do for you?");

        TaskManager taskManager = new TaskManager();
        Scanner in = new Scanner(System.in);
        String userInput;

        while (true) {
            System.out.println(separator);
            System.out.print("> ");
            userInput = in.nextLine().strip();

            switch (userInput) {
            case "list":
                taskManager.printTaskList();
                break;
            case "bye":
                System.out.println("Bye. Hope to see you again soon!");
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
