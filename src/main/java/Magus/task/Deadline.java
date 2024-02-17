package Magus.task;

import Magus.exception.ArgumentNotFoundException;
import Magus.task.fileio.Parser;

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

    public static Deadline parseStoredTaskInfo(Parser parser) {
        String taskInfo = parser.getTaskInfo();
        String[] taskInfoSplit = Parser.split(taskInfo);
        if (taskInfoSplit.length != 2) {
            return null;
        }

        String description = taskInfoSplit[0];
        String end = taskInfoSplit[1];
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
        String deadlineInfo = String.format("%s\t|\t%s", super.toStoredString(), end);
        return String.format("%c\t|\t%s", getBadge(), deadlineInfo);
    }
}
