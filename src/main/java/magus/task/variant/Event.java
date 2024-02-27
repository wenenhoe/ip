package magus.task.variant;

import magus.exception.ArgumentNotFoundException;
import magus.task.Task;
import magus.task.storage.Parser;

import java.time.LocalDate;
import java.util.List;

import static magus.task.storage.Parser.DELIMITER;

public class Event extends Task {
    private final LocalDate start;
    private final LocalDate end;

    public Event(String description, LocalDate start, LocalDate end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    public static Event parse(String taskInfo) throws ArgumentNotFoundException {
        String commandFromArg = "/from";
        String commandToArg = "/to";

        List<String> infoList = List.of(taskInfo.split(" "));
        int commandFromIndex = infoList.indexOf(commandFromArg);
        if (commandFromIndex == -1) {
            // Unable to find the arg /from
            String errorContext = String.format("Missing /from argument in \"%s\"", taskInfo);
            throw new ArgumentNotFoundException(errorContext);
        }
        int commandToIndex = infoList.indexOf(commandToArg);
        if (commandToIndex == -1) {
            // Unable to find the arg /to
            String errorContext = String.format("Missing /to argument in \"%s\"", taskInfo);
            throw new ArgumentNotFoundException(errorContext);
        }

        List<String> descriptionList = infoList.subList(0, commandFromIndex);
        String description = String.join(" ", descriptionList);

        List<String> startList = infoList.subList(commandFromIndex + 1, commandToIndex);
        String startString = String.join(" ", startList);
        LocalDate start = LocalDate.parse(startString);

        List<String> endList = infoList.subList(commandToIndex + 1, infoList.size());
        String endString = String.join(" ", endList);
        LocalDate end = LocalDate.parse(endString);

        return new Event(description, start, end);
    }

    public static Event parseStoredTaskInfo(Parser parser) {
        String taskInfo = parser.getTaskInfo();
        String[] taskInfoSplit = Parser.split(taskInfo);
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
