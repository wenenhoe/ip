package magus.console;

import java.util.Scanner;

/**
 * Handles getting user input from and printing out on the console
 */
public class Console {
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Prints a new separator and console prompt and gets user input
     *
     * @return User input from console
     */
    public static String getUserInput() {
        String separator = "-----------------------------------------------";
        System.out.println(separator);
        System.out.print("> ");
        return SCANNER.nextLine().strip();
    }

    /**
     * Prints welcome message on console
     */
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

    /**
     * Prints response on console with a single tab
     *
     * @param response String to be printed on console
     */
    public static void printResponse(String response) {
        int tabCount = 1;
        printResponse(response, tabCount);
    }

    /**
     * Prints response on console with variable number of tabs
     *
     * @param response String to be printed on console
     * @param tabCount Number of tabs to be included before printing <code>response</code>
     */
    public static void printResponse(String response, int tabCount) {
        System.out.println("\t".repeat(tabCount) + response);
    }

    /**
     * Prints warning on console
     *
     * @param message Warning message to be printed on console
     * @param tabCount Number of tabs to be included before printing <code>message</code>
     */
    public static void printWarning(String message, int tabCount) {
        String formattedString = String.format("**** %s ****", message);
        System.out.println("\t".repeat(tabCount) + formattedString);
    }

    /**
     * Prints error on console
     *
     * @param message Error message to be printed on console
     * @param cause Cause of error, has to be of <code>Throwable</code> type
     */
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
