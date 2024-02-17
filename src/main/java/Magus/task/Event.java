package Magus.task;

import Magus.exception.ArgumentNotFoundException;

import java.util.List;

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
        String dateTimeInfo = String.format("%s\t|\t%s", start, end);
        String eventInfo = String.format("%s\t|\t%s", super.toStoredString(), dateTimeInfo);
        return String.format("%c\t|\t%s", getBadge(), eventInfo);
    }
}
