package magus.task.variant;

import magus.console.Parser;
import magus.exception.ArgumentNotFoundException;
import magus.exception.UnknownArgumentException;
import magus.task.Task;

import java.time.format.DateTimeParseException;
import java.util.Map;

import static magus.task.storage.Parser.DELIMITER;

/**
 * Task variant Todo that stores a description
 */
public class Todo extends Task {
    /**
     * Constructor for task variant Todo
     *
     * @param description Describe the todo task
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Parses the console input to construct Todo
     *
     * @param parser Console parser that parsed user input
     * @return Todo object constructed from a console input
     * @throws ArgumentNotFoundException No description found
     * @throws UnknownArgumentException Unknown argument <code>/?</code> specified
     * @see magus.console.Parser
     */
    public static Todo parseConsoleTaskInfo(Parser parser)
            throws ArgumentNotFoundException, UnknownArgumentException {
        String descriptionCommand = "";
        Map<String, String> parsedArgs = parser.parseAdditionalInput(true);
        String description = Parser.getParsedArgsValue(parsedArgs, descriptionCommand, "description");
        return new Todo(description);
    }

    /**
     * Parses the stored input to construct Todo
     *
     * @param description Description of Todo
     * @param isDone Whether Todo is marked as done
     * @return Todo object restored from a stored string
     * @see #toStoredString()
     */
    public static Todo parseStoredTaskInfo(String description, boolean isDone) {
        boolean hasNoDescription = description.isEmpty();
        if (hasNoDescription) {
            return null;
        }

        Todo todo = new Todo(description);
        if (isDone) {
            todo.markAsDone();
        }
        return todo;
    }

    @Override
    public char getBadge() {
        return 'T';
    }

    @Override
    public String toString() {
        return String.format("[%c]%s", getBadge(), super.toString());
    }

    @Override
    public String toStoredString() {
        String formatString = "%c" + DELIMITER + "%s";
        return String.format(formatString, getBadge(), super.toStoredString());
    }
}
