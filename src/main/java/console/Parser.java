package console;

public class Parser {
    @Deprecated
    private final String input;
    private final Command command;
    private final String additionalInput;
    private final boolean isSingleWord;

    public Parser(String input) {
        this.input = input;

        // Parse first word to get command

        String firstWord = input;
        int firstSpaceIndex = input.indexOf(" ");
        if (firstSpaceIndex != -1) {
            firstWord = input.substring(0, firstSpaceIndex);
        }
        this.command = Command.getEnum(firstWord);

        // Parse additional input

        int additionalInputIndex = command.toString().length() + 1;
        boolean isNotDefaultCommand = command != Command.DEFAULT;
        boolean isAdditionalInputExists = additionalInputIndex < input.length();
        if (isNotDefaultCommand && isAdditionalInputExists) {
            // if it is non-DEFAULT commands and there is additional input
            this.additionalInput = input.substring(additionalInputIndex);
        } else {
            this.additionalInput = "";
        }

        this.isSingleWord = additionalInput.isEmpty();
    }

    @Deprecated
    public String getInput() {
        return input;
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
