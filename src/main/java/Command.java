public enum Command {
    DEFAULT,
    LIST,
    BYE,
    MARK,
    UNMARK;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public static Command getEnum(String name) {
        try {
            return Command.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException ok) {
            return Command.DEFAULT;
        }
    }
}
