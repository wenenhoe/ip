package magus.console;

public class Parser {
    private final Command command;
    private final String additionalInput;

    public Parser(String input) {
        // Parse first word to get command
        String firstWord = input;
        int firstSpaceIndex = input.indexOf(" ");
        if (firstSpaceIndex != -1) {
            firstWord = input.substring(0, firstSpaceIndex);
        }
        Command command = Command.getEnum(firstWord);

        // Parse additional input
        String additionalInput = "";
        int additionalInputIndex = command.toString().length() + 1;
        boolean isUnknownCommand = command == Command.UNKNOWN;
        boolean hasAdditionalInput = additionalInputIndex < input.length();
        if (!isUnknownCommand && hasAdditionalInput) {
            additionalInput = input.substring(additionalInputIndex);
        }
        this.additionalInput = additionalInput;

        // Verify Command.LIST and Command.BYE have no additionalInput
        boolean isListCommand = command == Command.LIST;
        boolean isByeCommand = command == Command.BYE;
        boolean isListOrByeCommand = isListCommand || isByeCommand;
        boolean isSingleWord = additionalInput.isEmpty(); // No additional input
        if (isListOrByeCommand && !isSingleWord) {
            command = Command.UNKNOWN;
        }
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public String getAdditionalInput() {
        return additionalInput;
    }

    public static int parseInt(String candidate) {
        try {
            return Integer.parseInt(candidate);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
