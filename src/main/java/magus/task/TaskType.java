package magus.task;

/**
 * Task types implemented
 */
public enum TaskType {
    UNKNOWN,
    TODO,
    DEADLINE,
    EVENT;

    /**
     * Returns enum value based on given badge character
     *
     * @param badge Task type badge
     * @return Corresponding enum value
     * @throws IllegalStateException Unknown task type badge specified
     */
    public static TaskType getEnum(char badge) throws IllegalStateException {
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

    /**
     * Returns enum value based on given task type name
     *
     * @param name Task type name
     * @return Corresponding enum value or <code>UNKNOWN</code>
     */
    public static TaskType getEnum(String name) {
        try {
            return TaskType.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException ok) {
            return TaskType.UNKNOWN;
        }
    }
}
