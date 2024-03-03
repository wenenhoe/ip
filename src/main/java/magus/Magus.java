package magus;

import magus.console.Command;
import magus.console.CommandManager;
import magus.console.Console;
import magus.console.Parser;
import magus.exception.CommandNotFoundException;
import magus.exception.UnknownArgumentException;
import magus.task.TaskManager;

/**
 * Chatbot and task manager, all-in-one program
 */
public class Magus {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Console.printWelcomeMessage();

        while (true) {
            String input = Console.getUserInput();
            Parser parser = new Parser(input);
            try {
                CommandManager.processInput(parser, taskManager);
            } catch (CommandNotFoundException | UnknownArgumentException e) {
                String errorScope = "MAGUS";
                Console.printError(errorScope, e);
            }

            boolean isExitProgram = parser.getCommand() == Command.BYE;
            if (isExitProgram) {
                // Exit loop when bye command issued
                break;
            }
        }
    }
}
