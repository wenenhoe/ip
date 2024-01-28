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

        String[] todoList = new String[100];
        int todoCount = 0;

        String userInput = "";
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println(separator);
            System.out.print("> ");
            userInput = in.nextLine().strip();

            if (userInput.equals("list")) {
                for (int i = 0; i < todoCount; i++) {
                    System.out.println("\t" + (i + 1) + ". " + todoList[i]);
                }
            } else if (userInput.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else {
                todoList[todoCount++] = userInput;
                System.out.println("\tadded: " + userInput);
            }
        }
    }
}
