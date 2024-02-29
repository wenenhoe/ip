package magus.task.variant;

import magus.console.Parser;
import magus.exception.ArgumentNotFoundException;
import magus.exception.UnknownArgumentException;
import magus.task.Task;

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

    public static Event parseConsoleTaskInfo(Parser parser)
            throws ArgumentNotFoundException, DateTimeParseException, UnknownArgumentException {
        String descriptionCommand = "";
        String fromCommand = "/from";
        String toCommand = "/to";
        Map<String, String> parsedArgs = parser.parseAdditionalInput(true, fromCommand, toCommand);

        String description = Parser.getParsedArgsValue(parsedArgs, descriptionCommand, "description");
        String startString = Parser.getParsedArgsValue(parsedArgs, fromCommand);
        LocalDate start = LocalDate.parse(startString);
        String endString = Parser.getParsedArgsValue(parsedArgs, toCommand);
        LocalDate end = LocalDate.parse(endString);

        return new Event(description, start, end);
    }

    public static Event parseStoredTaskInfo(String[] taskInfoSplit, boolean isDone) {
        if (taskInfoSplit.length != 3) {
            return null;
        }

        String description = taskInfoSplit[0];
        String startString = taskInfoSplit[1];
        LocalDate start = LocalDate.parse(startString);
        String endString = taskInfoSplit[2];
        LocalDate end = LocalDate.parse(endString);
        Event event = new Event(description, start, end);

        if (isDone) {
            event.markAsDone();
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
