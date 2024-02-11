package task;

import java.util.List;

public class Deadline extends Task {
    private final String end;

    public Deadline(String description, String end) {
        super(description);
        this.end = end;
    }

    public static Deadline parse(String taskInfo) {
        String commandArg = "/by";

        List<String> infoList = List.of(taskInfo.split(" "));
        int commandIndex = infoList.indexOf(commandArg);
        if (commandIndex == -1) {
            // Unable to find the arg /by
            return null;
        }

        List<String> descriptionList = infoList.subList(0, commandIndex);
        String description = String.join(" ", descriptionList);

        List<String> byList = infoList.subList(commandIndex + 1, infoList.size());
        String end = String.join(" ", byList);

        return new Deadline(description, end);
    }

    @Override
    public String toString() {
        String dateTimeInfo = String.format("(by: %s)", end);
        return String.format("[D]%s %s", super.toString(), dateTimeInfo);
    }
}
