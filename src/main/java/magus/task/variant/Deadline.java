package magus.task.variant;

import magus.console.Parser;
import magus.exception.ArgumentNotFoundException;
import magus.exception.UnknownArgumentException;
import magus.task.Task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

import static magus.task.storage.Parser.DELIMITER;

/**
 * Task variant Deadline that stores a description and the end date
 */
public class Deadline extends Task {
    private final LocalDate end;

    /**
     * Constructor for task variant Deadline
     *
     * @param description Describe the deadline
     * @param end End date for task deadline
     */
    public Deadline(String description, LocalDate end) {
        super(description);
        this.end = end;
    }

    /**
     * Parses the console input to construct Deadline
     *
     * @param parser Console parser that parsed user input
     * @return Deadline object constructed from a console input
     * @throws ArgumentNotFoundException <code>/by</code> argument not found
     * @throws DateTimeParseException Invalid date specified with <code>/by</code> argument
     * @throws UnknownArgumentException Unknown argument <code>/?</code> specified
     * @see magus.console.Parser
     */
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

    /**
     * Parses the stored input to construct Deadline
     *
     * @param taskInfoSplit String array of each component to reconstruct Deadline
     * @param isDone Whether Deadline is marked as done
     * @return Deadline object restored from a stored string
     * @see #toStoredString()
     */
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
