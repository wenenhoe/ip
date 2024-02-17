package magus.exception;

public class CommandNotFoundException extends IllegalArgumentException {
    public CommandNotFoundException() {}

    public CommandNotFoundException(String message) {
        super(message);
    }
}
