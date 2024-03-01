package magus.task.storage;

import magus.task.TaskType;

import java.util.regex.Pattern;

/**
 * Handles parsing of stored task info.
 * Obtains the basic information needed to identify task type and status
 * before it is handed off to the relevant task type to parsed for additional
 * task info.
 */
public class Parser {
    /**
     * Delimiter for storing and parsing stored task info
     */
    public static final String DELIMITER = "\t|\t";
    private static final String DELIMITER_REGEX = Pattern.quote(DELIMITER);
    private TaskType taskType;
    private boolean isDone;
    private String taskInfo;
    private String[] splitTaskInfo;

    /**
     * Constructor for parsing stored task info to obtain
     * task type, status and additional task info
     * @param storedString String of stored task info
     */
    public Parser(String storedString) {
        int maxSplitCount = 3; // Obtain the task badge and task completion status
        String[] storedStringSplit = storedString.split(DELIMITER_REGEX, maxSplitCount);

        if (storedStringSplit.length != maxSplitCount) {
            return; // Invalid stored string
        }

        char badge = storedStringSplit[0].charAt(0);
        taskType = TaskType.getEnum(badge);

        isDone = Boolean.parseBoolean(storedStringSplit[1]);
        taskInfo = storedStringSplit[2];
        splitTaskInfo = taskInfo.split(DELIMITER_REGEX);
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public String getTaskInfo() {
        return taskInfo;
    }

    public boolean isDone() {
        return isDone;
    }

    public String[] getSplitTaskInfo() {
        return splitTaskInfo;
    }
}
