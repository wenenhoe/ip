package task;

import exception.ArgumentNotFoundException;

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
    public String toString() {
        String dateTimeInfo = String.format(" (from: %s to: %s)", start, end);
        return String.format("[E]%s %s", super.toString(), dateTimeInfo);
    }
}
