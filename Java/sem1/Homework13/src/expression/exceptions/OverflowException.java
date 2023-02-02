package expression.exceptions;

public class OverflowException extends EvaluatingException {

    public OverflowException(String message) {
        super(message);
    }

    public OverflowException(String message, Throwable cause) {
        super(message, cause);
    }

}
