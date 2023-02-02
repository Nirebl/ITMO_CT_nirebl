package parser;

public interface CharSource {
    boolean hasNext();
    char next();
    void back();
    ParsingException error(final String message);
    ParsingException error(ParsingException exception);
}
