package expression.exceptions;

public class DivideByZeroException  extends EvaluatingException {

    public DivideByZeroException(String message) {
        super(message);
    }

    public DivideByZeroException(String message, Throwable cause) {
        super(message, cause);
    }
}
