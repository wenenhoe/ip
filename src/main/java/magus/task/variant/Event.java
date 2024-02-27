package magus.task.variant;

import magus.exception.ArgumentNotFoundException;
import magus.task.Task;
import magus.task.storage.Parser;

import java.util.Map;

import static magus.task.storage.Parser.DELIMITER;

public class Event extends Task {
    private final String start;
    private final String end;

    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    public static Event parseConsoleTaskInfo(magus.console.Parser parser)
            throws ArgumentNotFoundException {
        String descriptionCommand = "";
        String fromCommand = "/from";
        String toCommand = "/to";
        Map<String, String> parsedArgs = parser.parseAdditionalInput(
                true,
                fromCommand,
                toCommand);

        String description = parsedArgs.get(descriptionCommand);
        if (description.isEmpty()) {
            String errorContext = "Missing description";
            throw new ArgumentNotFoundException(errorContext);
        }

        String start = parsedArgs.get(fromCommand);
        if (start.isEmpty()) {
            String errorContext = String.format("Missing info specified in %s", fromCommand);
            throw new ArgumentNotFoundException(errorContext);
        }

        String end = parsedArgs.get(toCommand);
        if (end.isEmpty()) {
            String errorContext = String.format("Missing info specified in %s", toCommand);
            throw new ArgumentNotFoundException(errorContext);
        }

        return new Event(description, start, end);
    }

    public static Event parseStoredTaskInfo(Parser parser) {
        String taskInfo = parser.getTaskInfo();
        String[] taskInfoSplit = Parser.split(taskInfo);
        if (taskInfoSplit.length != 3) {
            return null;
        }

        String description = taskInfoSplit[0];
        String start = taskInfoSplit[1];
        String end = taskInfoSplit[2];
        Event event = new Event(description, start, end);

        boolean isDone = parser.isDone();
        if (isDone) {
            event.markAsDone();
        } else {
            event.unmarkAsDone();
        }

        return event;
    }

    @Override
    public char getBadge() {
        return 'E';
    }

    @Override
    public String toString() {
        String dateTimeInfo = String.format(" (from: %s to: %s)", start, end);
        return String.format("[%c]%s %s", getBadge(), super.toString(), dateTimeInfo);
    }

    @Override
    public String toStoredString() {
        String ssFormatString = "%s" + DELIMITER + "%s"; // Double string format
        String dateTimeInfo = String.format(ssFormatString, start, end);
        String eventInfo = String.format(ssFormatString, super.toStoredString(), dateTimeInfo);

        String csFormatString = "%c" + DELIMITER + "%s"; // Single char and string format
        return String.format(csFormatString, getBadge(), eventInfo);
    }
}
