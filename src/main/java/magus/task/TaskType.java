package magus.task;

public enum TaskType {
    UNKNOWN,
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

    public static TaskType getEnum(String name) {
        try {
            return TaskType.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException ok) {
            return TaskType.UNKNOWN;
        }
    }
}
