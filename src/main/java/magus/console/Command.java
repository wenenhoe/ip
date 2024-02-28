package magus.console;

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

    public static Command getEnum(String name) {
        try {
            return Command.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException ok) {
            return Command.UNKNOWN;
        }
    }
}
