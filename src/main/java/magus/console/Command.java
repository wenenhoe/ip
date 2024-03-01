package magus.console;

/**
 * Commands supported by chatbot
 */
public enum Command {
    UNKNOWN,
    LIST,
    FIND,
    BYE,
    MARK,
    UNMARK,
    DELETE,
    TODO,
    DEADLINE,
    EVENT;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    /**
     * Returns enum value based on given command name
     *
     * @param name Command name
     * @return Corresponding enum value or <code>UNKNOWN</code>
     */
    public static Command getEnum(String name) {
        try {
            return Command.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException ok) {
            return Command.UNKNOWN;
        }
    }
}
