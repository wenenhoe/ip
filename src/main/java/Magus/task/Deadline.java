package Magus.task;

import Magus.exception.ArgumentNotFoundException;

import java.util.List;

public class Deadline extends Task {
    private final String end;

    public Deadline(String description, String end) {
        super(description);
        this.end = end;
    }

    public static Deadline parse(String taskInfo) throws ArgumentNotFoundException {
        String commandArg = "/by";

        List<String> infoList = List.of(taskInfo.split(" "));
        int commandIndex = infoList.indexOf(commandArg);
        if (commandIndex == -1) {
            // Unable to find the arg /by
            throw new ArgumentNotFoundException(taskInfo);
        }

        List<String> descriptionList = infoList.subList(0, commandIndex);
        String description = String.join(" ", descriptionList);

        List<String> byList = infoList.subList(commandIndex + 1, infoList.size());
        String end = String.join(" ", byList);

        return new Deadline(description, end);
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
}
