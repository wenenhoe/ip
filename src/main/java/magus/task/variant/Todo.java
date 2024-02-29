package magus.task.variant;

import magus.exception.ArgumentNotFoundException;
import magus.exception.UnknownArgumentException;
import magus.task.Task;
import magus.task.storage.Parser;

import java.util.Map;

import static magus.task.storage.Parser.DELIMITER;

public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    public static Todo parseConsoleTaskInfo(magus.console.Parser parser)
            throws ArgumentNotFoundException, UnknownArgumentException {
        String descriptionCommand = "";
        Map<String, String> parsedArgs = parser.parseAdditionalInput(true);
        String description = parsedArgs.get(descriptionCommand);
        boolean hasNoDescription = description.isEmpty();
        if (hasNoDescription) {
            String errorContext = "Missing description";
            throw new ArgumentNotFoundException(errorContext);
        }
        return new Todo(description);
    }

    public static Todo parseStoredTaskInfo(Parser parser) {
        String description = parser.getTaskInfo();
        boolean hasNoDescription = description.isEmpty();
        if (hasNoDescription) {
            return null;
        }
        Todo todo = new Todo(description);

        boolean isDone = parser.isDone();
        if (isDone) {
            todo.markAsDone();
        } else {
            todo.unmarkAsDone();
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
