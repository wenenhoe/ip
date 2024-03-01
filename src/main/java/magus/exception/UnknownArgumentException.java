package magus.exception;

/**
 * Exception thrown when unknown argument specified
 */
public class UnknownArgumentException extends Exception {
    public UnknownArgumentException() {}

    public UnknownArgumentException(String message) {
        super(message);
    }
}
