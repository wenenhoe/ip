package magus.console;

import java.util.Scanner;

public class Console {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static String getUserInput() {
        String separator = "-----------------------------------------------";
        System.out.println(separator);
        System.out.print("> ");
        return SCANNER.nextLine().strip();
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
        int tabCount = 1;
        printResponse(response, tabCount);
    }

    public static void printResponse(String response, int tabCount) {
        System.out.println("\t".repeat(tabCount) + response);
    }

    public static void printWarning(String message, int tabCount) {
        String formattedString = String.format("**** %s ****", message);
        System.out.println("\t".repeat(tabCount) + formattedString);
    }

    public static void printError(Throwable cause) {
        System.out.println(cause.toString());
    }

    public static void printError(String message, Throwable cause) {
        String exceptionName = cause.getClass().getName();
        String exceptionDetails = cause.getMessage();
        String errorMsg = String.format("%s %s: %s",
                message,
                exceptionName,
                exceptionDetails);
        System.out.println(errorMsg);
    }
}
