import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Magus {
    public static void printTaskList(List<Task> taskList) {
        Task[] tasks = taskList.toArray(new Task[0]);

        int taskNum = 1;
        System.out.println("\tHere are the tasks in your list:");
        for (Task task : tasks) {
            System.out.println("\t" + taskNum++ + "." + task.toString());
        }
    }

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

        List<Task> taskList = new ArrayList<Task>();
        String userInput = "";
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println(separator);
            System.out.print("> ");
            userInput = in.nextLine().strip();

            if (userInput.equals("list")) {
                printTaskList(taskList);
            } else if (userInput.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else {
                Task t = new Task(userInput);
                taskList.add(t);
                System.out.println("\tadded: " + userInput);
            }
        }
    }
}
