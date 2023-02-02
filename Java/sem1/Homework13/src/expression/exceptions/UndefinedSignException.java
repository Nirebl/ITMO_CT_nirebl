package expression.exceptions;

public class UndefinedSignException  extends ParsingException {

    public UndefinedSignException(String message) {
        super(message);
    }

    public UndefinedSignException(String message, Throwable cause) {
        super(message, cause);
    }
}
