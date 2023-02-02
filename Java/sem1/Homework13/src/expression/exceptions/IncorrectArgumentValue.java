package expression.exceptions;

public class IncorrectArgumentValue extends EvaluatingException {

    public IncorrectArgumentValue(String message) {
        super(message);
    }

    public IncorrectArgumentValue(String message, Throwable cause) {
        super(message, cause);
    }

}
