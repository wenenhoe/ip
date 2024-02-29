package magus.task.variant;

import magus.console.Parser;
import magus.exception.ArgumentNotFoundException;
import magus.exception.UnknownArgumentException;
import magus.task.Task;

import java.util.Map;

import static magus.task.storage.Parser.DELIMITER;

public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    public static Todo parseConsoleTaskInfo(Parser parser)
            throws ArgumentNotFoundException, UnknownArgumentException {
        String descriptionCommand = "";
        Map<String, String> parsedArgs = parser.parseAdditionalInput(true);
        String description = Parser.getParsedArgsValue(parsedArgs, descriptionCommand, "description");
        return new Todo(description);
    }

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
