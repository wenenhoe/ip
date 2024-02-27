package magus.task.variant;

import magus.exception.ArgumentNotFoundException;
import magus.task.Task;
import magus.task.storage.Parser;

import java.time.LocalDate;
import java.util.List;

import static magus.task.storage.Parser.DELIMITER;

public class Deadline extends Task {
    private final LocalDate end;

    public Deadline(String description, LocalDate end) {
        super(description);
        this.end = end;
    }

    public static Deadline parse(String taskInfo) throws ArgumentNotFoundException {
        String commandArg = "/by";

        List<String> infoList = List.of(taskInfo.split(" "));
        int commandIndex = infoList.indexOf(commandArg);
        if (commandIndex == -1) {
            // Unable to find the arg /by
            String errorContext = String.format("Missing /by argument in \"%s\"", taskInfo);
            throw new ArgumentNotFoundException(errorContext);
        }

        List<String> descriptionList = infoList.subList(0, commandIndex);
        String description = String.join(" ", descriptionList);

        List<String> byList = infoList.subList(commandIndex + 1, infoList.size());
        String endString = String.join(" ", byList);
        LocalDate end = LocalDate.parse(endString);

        return new Deadline(description, end);
    }

    public static Deadline parseStoredTaskInfo(Parser parser) {
        String taskInfo = parser.getTaskInfo();
        String[] taskInfoSplit = Parser.split(taskInfo);
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
