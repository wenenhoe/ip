package Magus.task.fileio;

import Magus.task.TaskType;

import java.util.List;

public class Parser {
    private TaskType taskType;
    private String additionalInfo;
    private static final String DELIMITER =  "\t|\t";

    public Parser(String storedString) {
        int maxSplitCount = 2; // Just to obtain the task badge
        String[] storedStringSplit = storedString.split(DELIMITER, maxSplitCount);

        if (storedStringSplit.length != 2) {
            return; // Invalid stored string, does not contain task type
        }

        char badge = storedStringSplit[0].charAt(0);
        taskType = TaskType.getEnum(badge);

        additionalInfo = storedStringSplit[1];
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public static String[] split(String taskInfo) {
        return taskInfo.split(DELIMITER);
    }
}
