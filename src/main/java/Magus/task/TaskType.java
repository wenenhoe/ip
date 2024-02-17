package Magus.task;

public enum TaskType {
    TODO,
    DEADLINE,
    EVENT;

    public static TaskType getEnum(char badge) {
        switch (badge) {
        case 'D':
            return TaskType.DEADLINE;
        case 'E':
            return TaskType.EVENT;
        case 'T':
            return TaskType.TODO;
        default:
            throw new IllegalStateException("Unexpected value: " + badge);
        }
    }
}
