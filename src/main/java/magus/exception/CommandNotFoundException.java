package magus.exception;

/**
 * Exception thrown when command specified is not recognised
 */
public class CommandNotFoundException extends IllegalArgumentException {
    public CommandNotFoundException() {}

    public CommandNotFoundException(String message) {
        super(message);
    }
}
