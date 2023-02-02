package expression.exceptions;

public class UnsupportedOperationException extends ParsingException {

    public UnsupportedOperationException(String message) {
        super(message);
    }

    public UnsupportedOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
