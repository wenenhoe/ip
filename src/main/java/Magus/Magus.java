package Magus;

import Magus.console.Command;
import Magus.console.CommandManager;
import Magus.console.Console;
import Magus.console.Parser;
import Magus.exception.CommandNotFoundException;
import Magus.task.TaskManager;

public class Magus {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Console.printWelcomeMessage();
        boolean isExitProgram = false;

        while (!isExitProgram) {
            String input = Console.getUserInput();
            Parser parser = new Parser(input);
            try {
                CommandManager.processInput(parser, taskManager);
            } catch (CommandNotFoundException e) {
                Console.printError(e);
            }

            // Exit when bye command issued
            isExitProgram = parser.getCommand() == Command.BYE;
        }
    }
}
