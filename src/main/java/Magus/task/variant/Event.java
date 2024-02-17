package Magus.task.variant;

import Magus.exception.ArgumentNotFoundException;
import Magus.task.Task;
import Magus.task.fileio.Parser;

import java.util.List;

import static Magus.task.fileio.Parser.DELIMITER;

public class Event extends Task {
    private final String start;
    private final String end;

    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    public static Event parse(String taskInfo) throws ArgumentNotFoundException {
        String commandFromArg = "/from";
        String commandToArg = "/to";

        List<String> infoList = List.of(taskInfo.split(" "));
        int commandFromIndex = infoList.indexOf(commandFromArg);
        int commandToIndex = infoList.indexOf(commandToArg);
        if (commandFromIndex == -1 || commandToIndex == -1) {
            // Unable to find either of the 2 args /from and /to
            throw new ArgumentNotFoundException(taskInfo);
        }

        List<String> descriptionList = infoList.subList(0, commandFromIndex);
        String description = String.join(" ", descriptionList);

        List<String> startList = infoList.subList(commandFromIndex + 1, commandToIndex);
        String start = String.join(" ", startList);

        List<String> endList = infoList.subList(commandToIndex + 1, infoList.size());
        String end = String.join(" ", endList);

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
