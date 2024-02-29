package magus.task.variant;

import magus.exception.ArgumentNotFoundException;
import magus.exception.UnknownArgumentException;
import magus.task.Task;
import magus.task.storage.Parser;

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

    public static Deadline parseConsoleTaskInfo(magus.console.Parser parser)
            throws ArgumentNotFoundException, DateTimeParseException, UnknownArgumentException {
        String descriptionCommand = "";
        String byCommand = "/by";
        Map<String, String> parsedArgs = parser.parseAdditionalInput(
                true,
                byCommand);

        String description = parsedArgs.get(descriptionCommand);
        if (description.isEmpty()) {
            String errorContext = "Missing description";
            throw new ArgumentNotFoundException(errorContext);
        }

        String endString = parsedArgs.get(byCommand);
        if (endString.isEmpty()) {
            String errorContext = String.format("Missing info specified in %s", byCommand);
            throw new ArgumentNotFoundException(errorContext);
        }
        LocalDate end = LocalDate.parse(endString);

        return new Deadline(description, end);
    }

    public static Deadline parseStoredTaskInfo(Parser parser) {
        String[] taskInfoSplit = parser.getSplitTaskInfo();
        if (taskInfoSplit.length != 2) {
            return null;
        }

        String description = taskInfoSplit[0];
        String endString = taskInfoSplit[1];
        LocalDate end = LocalDate.parse(endString);
        Deadline deadline = new Deadline(description, end);

        boolean isDone = parser.isDone();
        if (isDone) {
            deadline.markAsDone();
        } else {
            deadline.unmarkAsDone();
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
