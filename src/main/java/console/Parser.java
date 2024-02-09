package console;

public class Parser {
    private final Command command;
    private final String additionalInput;
    private final boolean isSingleWord;

    public Parser(String input) {
        // Parse first word to get command

        String firstWord = input;
        int firstSpaceIndex = input.indexOf(" ");
        if (firstSpaceIndex != -1) {
            firstWord = input.substring(0, firstSpaceIndex);
        }
        this.command = Command.getEnum(firstWord);

        // Parse additional input

        int additionalInputIndex = command.toString().length() + 1;
        boolean isNotUnknownCommand = command != Command.UNKNOWN;
        boolean isAdditionalInputExists = additionalInputIndex < input.length();
        if (isNotUnknownCommand && isAdditionalInputExists) {
            // if it is not UNKNOWN commands and there is additional input
            this.additionalInput = input.substring(additionalInputIndex);
        } else {
            this.additionalInput = "";
        }

        // No additional input would be a single word command

        this.isSingleWord = additionalInput.isEmpty();
    }

    public Command getCommand() {
        return command;
    }

    public String getAdditionalInput() {
        return additionalInput;
    }

    public boolean isSingleWord() {
        return isSingleWord;
    }

    public static int parseInt(String candidate) {
        try {
            return Integer.parseInt(candidate);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
