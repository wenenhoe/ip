package console;

import java.util.Scanner;

public class Console {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getUserInput() {
        String separator = "-----------------------------------------------";
        System.out.println(separator);
        System.out.print("> ");
        return scanner.nextLine().strip();
    }

    public static void printWelcomeMessage() {
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
    }

    public static void printResponse(String response) {
        printResponse(response, 1);
    }

    public static void printResponse(String response, int tabCount) {
        System.out.println("\t".repeat(tabCount) + response);
    }
}
