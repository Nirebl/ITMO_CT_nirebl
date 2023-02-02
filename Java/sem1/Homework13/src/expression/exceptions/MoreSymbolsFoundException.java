package expression.exceptions;

public class MoreSymbolsFoundException extends ParsingException {

    public MoreSymbolsFoundException(String message) {
        super(message);
    }

    public MoreSymbolsFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
