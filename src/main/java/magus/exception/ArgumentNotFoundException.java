package magus.exception;

/**
 * Exception thrown when argument required is not found
 */
public class ArgumentNotFoundException extends Exception {
    public ArgumentNotFoundException() {}

    public ArgumentNotFoundException(String message) {
        super(message);
    }
}
