package expression.exceptions;

public class DivideByZeroException extends EvaluatingException {
    private static final String msg = "Divide by zero";

    public DivideByZeroException() {
        super(msg);
    }

    public DivideByZeroException(Throwable cause) {
        super(msg, cause);
    }
}
