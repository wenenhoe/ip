package magus.task.variant;

import magus.exception.ArgumentNotFoundException;
import magus.exception.UnknownArgumentException;
import magus.task.Task;
import magus.task.storage.Parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

import static magus.task.storage.Parser.DELIMITER;

public class Event extends Task {
    private final LocalDate start;
    private final LocalDate end;

    public Event(String description, LocalDate start, LocalDate end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    public static Event parseConsoleTaskInfo(magus.console.Parser parser)
            throws ArgumentNotFoundException, DateTimeParseException, UnknownArgumentException {
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

        String startString = parsedArgs.get(fromCommand);
        if (startString.isEmpty()) {
            String errorContext = String.format("Missing info specified in %s", fromCommand);
            throw new ArgumentNotFoundException(errorContext);
        }
        LocalDate start = LocalDate.parse(startString);

        String endString = parsedArgs.get(toCommand);
        if (endString.isEmpty()) {
            String errorContext = String.format("Missing info specified in %s", toCommand);
            throw new ArgumentNotFoundException(errorContext);
        }
        LocalDate end = LocalDate.parse(endString);

        return new Event(description, start, end);
    }

    public static Event parseStoredTaskInfo(Parser parser) {
        String[] taskInfoSplit = parser.getSplitTaskInfo();
        if (taskInfoSplit.length != 3) {
            return null;
        }

        String description = taskInfoSplit[0];
        String startString = taskInfoSplit[1];
        LocalDate start = LocalDate.parse(startString);
        String endString = taskInfoSplit[2];
        LocalDate end = LocalDate.parse(endString);
        Event event = new Event(description, start, end);

        boolean isDone = parser.isDone();
        if (isDone) {
            event.markAsDone();
        } else {
            event.unmarkAsDone();
        }

        return event;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
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
