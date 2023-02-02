package expression.exceptions;

public class OverflowException extends EvaluatingException {
    private static final String msg = "Overflow";

    public OverflowException() {
        super(msg);
    }

    public OverflowException(Throwable cause) {
        super(msg, cause);
    }

}
