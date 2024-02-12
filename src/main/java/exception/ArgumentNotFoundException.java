package exception;

public class ArgumentNotFoundException extends Exception {
    public ArgumentNotFoundException() {}

    public ArgumentNotFoundException(String message) {
        super(message);
    }
}
