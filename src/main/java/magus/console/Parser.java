package magus.console;

import magus.exception.ArgumentNotFoundException;
import magus.exception.UnknownArgumentException;
import magus.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Parses user input from console into command and additional input
 */
public class Parser {
    private final String commandCandidate;
    private final Command command;
    private final String additionalInput;
    private final List<String> additionalInputList;

    /**
     * Constructor for Parser that parses the user input as such: first word is the Command
     * and remaining words are additional input
     *
     * @param input User input from console
     */
    public Parser(String input) {
        // Parse first word to get command
        String firstWord;
        int firstSpaceIndex = input.indexOf(" ");
        if (firstSpaceIndex != -1) {
            // Multiple words found, taking first word
            firstWord = input.substring(0, firstSpaceIndex);
        } else {
            // Single word input, no space
            firstWord = input;
        }
        commandCandidate = firstWord;
        Command command = Command.getEnum(commandCandidate);

        // Parse additional input
        String additionalInput = "";
        int additionalInputIndex = command.toString().length() + 1;
        boolean isUnknownCommand = command == Command.UNKNOWN;
        boolean hasAdditionalInput = additionalInputIndex < input.length();
        if (!isUnknownCommand && hasAdditionalInput) {
            additionalInput = input.substring(additionalInputIndex);
        }
        this.additionalInput = additionalInput;
        this.additionalInputList = List.of(additionalInput.split(" "));

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

    public String getCommandCandidate() {
        return commandCandidate;
    }

    public Command getCommand() {
        return command;
    }

    public String getAdditionalInput() {
        return additionalInput;
    }

    /**
     * Parses additional input based on keyword arguments such as /from, /to, /by etc.
     *
     * @param isIncludeFirstParam Whether to include the first unnamed parameter (i.e. no keyword argument)
     * @param keywordArgs Keyword arguments such as /from, /to, /by etc.
     * @return A map of keyword argument to its corresponding value
     * @throws ArgumentNotFoundException Specified keyword args specified not found
     * @throws UnknownArgumentException Unknown keyword args found in input
     * @see #hasUnknownArgument(String[])
     * @see #getArgIndexes(String[]) 
     * @see #getArgPositions(boolean, SortedMap) 
     * @see #getArgs(Map) 
     */
    public Map<String, String> parseAdditionalInput(boolean isIncludeFirstParam, String... keywordArgs)
            throws ArgumentNotFoundException, UnknownArgumentException {
        boolean hasUnknownArgs = hasUnknownArgument(keywordArgs);
        if (hasUnknownArgs) {
            String errorContext = String.format("Unknown argument found in \"%s\"", additionalInput);
            throw new UnknownArgumentException(errorContext);
        }

        if (keywordArgs.length == 0) {
            Map<String, String> args = new HashMap<>();
            if (isIncludeFirstParam) {
                args.put("", additionalInput);
            }
            return args;
        }

        SortedMap<Integer, String> indexes = getArgIndexes(keywordArgs);
        Map<String, Pair<Integer, Integer>> positions = getArgPositions(
                isIncludeFirstParam,
                indexes);
        return getArgs(positions);
    }

    /**
     * Wrapper method on <code>Integer.parseInt</code>
     *
     * @param candidate Candidate string to try parsing to Integer
     * @return Integer parsed or 0 if unable to parse
     */
    public static int parseInt(String candidate) {
        try {
            return Integer.parseInt(candidate);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Obtains keyword argument value and checks if it is empty
     *
     * @param parsedArgs Parsed arguments returned from <code>parseAdditionalInput</code>
     * @param arg Keyword argument to obtain
     * @param argNames Name of keyword argument
     * @return Value of keyword argument value
     * @throws ArgumentNotFoundException Keyword argument's value if it is empty
     */
    public static String getParsedArgsValue(Map<String, String> parsedArgs, String arg, String... argNames)
            throws ArgumentNotFoundException {
        String argValue = parsedArgs.get(arg);
        String argName = arg;
        if (argValue.isEmpty()) {
            if (argNames.length != 0) {
                argName = argNames[0];
            }
            String errorContext = String.format("Missing info specified in %s", argName);
            throw new ArgumentNotFoundException(errorContext);
        }
        return argValue;
    }

    /**
     * Checks if keyword args found in additional input that is not in the <code>keywordArgs</code>
     *
     * @param keywordArgs Keyword arguments that are whitelisted
     * @return True if unknown arguments found and false if otherwise
     */
    private boolean hasUnknownArgument(String[] keywordArgs) {
        String pattern = "^/.+$";
        List<String> keywordArgsList = List.of(keywordArgs);
        List<String> unknownArgsList = additionalInputList.stream()
                .filter((input) -> input.matches(pattern)
                        && !keywordArgsList.contains(input))
                .collect(Collectors.toList());
        return !unknownArgsList.isEmpty();
    }

    /**
     * Obtains a sorted map of arguments and their corresponding indexes
     *
     * @param keywordArgs Keyword arguments to obtain
     * @return A sorted map of arguments and their corresponding indexes
     * @throws ArgumentNotFoundException Keyword args specified not found in additional input
     */
    private SortedMap<Integer, String> getArgIndexes(String[] keywordArgs) throws ArgumentNotFoundException {
        SortedMap<Integer, String> indexes = new TreeMap<>();

        for (String arg: keywordArgs) {
            int argIndex = additionalInputList.indexOf(arg);
            if (argIndex == -1) {
                // arg keyword not found in additional input
                String errorContext = String.format("Missing %s argument in \"%s\"", arg, additionalInput);
                throw new ArgumentNotFoundException(errorContext);
            }
            indexes.put(argIndex, arg);
        }

        return indexes;
    }

    /**
     * Obtains a map of keyword args and a pair of positions of each args (start and end indexes)
     *
     * @param isIncludeFirstParam Whether to include the first unnamed parameter (i.e. no keyword argument)
     * @param indexes A sorted map of arguments and their corresponding indexes
     * @return A map of keyword args and a pair of positions of each args (start and end indexes)
     */
    private Map<String, Pair<Integer, Integer>> getArgPositions(
            boolean isIncludeFirstParam,
            SortedMap<Integer, String> indexes) {
        Map<String, Pair<Integer, Integer>> positions = new HashMap<>();

        String arg;
        int startIndex, endIndex;
        if (isIncludeFirstParam) {
            arg = "";
            startIndex = 0;
            endIndex = indexes.firstKey();
            positions.put(arg, new Pair<>(startIndex, endIndex));
        } else {
            arg = indexes.get(indexes.firstKey());
            startIndex = indexes.firstKey() + 1; // position after keyword arg
            endIndex = -1;
        }

        for (Map.Entry<Integer, String> index: indexes.entrySet()) {
            if (endIndex == -1) {
                endIndex = startIndex; // allow next iteration to set endIndex
                continue;
            }

            endIndex = index.getKey();
            Pair<Integer, Integer> position = new Pair<>(startIndex, endIndex);
            positions.put(arg, position);
            arg = index.getValue();
            startIndex = endIndex + 1;
        }

        endIndex = additionalInputList.size();
        Pair<Integer, Integer> position = new Pair<>(startIndex, endIndex);
        positions.put(arg, position);

        return positions;
    }

    /**
     * Obtains a map of keyword args and their corresponding value
     *
     * @param positions A map of keyword args and a pair of positions of each args (start and end indexes)
     * @return A map of keyword args and their corresponding value
     */
    private Map<String, String> getArgs(Map<String, Pair<Integer, Integer>> positions) {
        Map<String, String> parsedArgs = new HashMap<>();

        for (Map.Entry<String, Pair<Integer, Integer>> position: positions.entrySet()) {
            String key = position.getKey();

            Pair<Integer, Integer> positionPair = position.getValue();
            int startIndex = positionPair.first;
            int endIndex = positionPair.second;
            List<String> argContentList = additionalInputList.subList(startIndex, endIndex);
            String value = String.join(" ", argContentList);

            parsedArgs.put(key, value);
        }

        return parsedArgs;
    }
}
