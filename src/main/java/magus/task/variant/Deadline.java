package magus.task.variant;

import magus.console.Parser;
import magus.exception.ArgumentNotFoundException;
import magus.exception.UnknownArgumentException;
import magus.task.Task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

import static magus.task.storage.Parser.DELIMITER;

public class Deadline extends Task {
    private final LocalDate end;

    public Deadline(String description, LocalDate end) {
        super(description);
        this.end = end;
    }

    public static Deadline parseConsoleTaskInfo(Parser parser)
            throws ArgumentNotFoundException, DateTimeParseException, UnknownArgumentException {
        String descriptionCommand = "";
        String byCommand = "/by";
        Map<String, String> parsedArgs = parser.parseAdditionalInput(true, byCommand);

        String description = Parser.getParsedArgsValue(parsedArgs, descriptionCommand, "description");
        String endString = Parser.getParsedArgsValue(parsedArgs, byCommand);
        LocalDate end = LocalDate.parse(endString);

        return new Deadline(description, end);
    }

    public static Deadline parseStoredTaskInfo(String[] taskInfoSplit, boolean isDone) {
        if (taskInfoSplit.length != 2) {
            return null;
        }

        String description = taskInfoSplit[0];
        String endString = taskInfoSplit[1];
        LocalDate end = LocalDate.parse(endString);
        Deadline deadline = new Deadline(description, end);

        if (isDone) {
            deadline.markAsDone();
        }
        return deadline;
    }

    public LocalDate getEnd() {
        return end;
    }

    @Override
    public char getBadge() {
        return 'D';
    }

    @Override
    public String toString() {
        String dateTimeInfo = String.format("(by: %s)", end);
        return String.format("[%c]%s %s", getBadge(), super.toString(), dateTimeInfo);
    }

    @Override
    public String toStoredString() {
        String ssFormatString = "%s" + DELIMITER + "%s"; // Double string format
        String deadlineInfo = String.format(ssFormatString, super.toStoredString(), end);

        String csFormatString = "%c" + DELIMITER + "%s"; // Single char and string format
        return String.format(csFormatString, getBadge(), deadlineInfo);
    }
}
