package parser;

public class ParserBase {
    protected static final char END = 0;
    private final CharSource source;
    private char ch;

    public ParserBase(CharSource source) {
        this.source = source;
        ch = source.next();
    }

    public ParserBase(CharSource source, boolean setFirstSymbol) {
        this.source = source;
        if(setFirstSymbol)
            ch = source.next();
    }

    protected char current() {
        return ch;
    }

    protected void back() {
        this.source.back();
        this.source.back();
        ch = source.next();
    }

    protected boolean test(final char expected) {
        return ch == expected;
    }

    protected char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }

        return false;
    }

    protected void expect(final char expected) throws ParsingException {
        if (!take(expected)) {
            throw source.error(String.format(
                    "Expected '%s', found '%s'",
                    expected, ch));
        }
    }

    protected void expect(final String expected) throws ParsingException {
        for (final char c : expected.toCharArray()) {
            expect(c);
        }
    }

    protected boolean between(final char min, final char max) {
        return min <= ch && ch <= max;
    }

    protected boolean end() {
        return test(END);
    }

    protected void skipWhitespace() {
        while (Character.isWhitespace(ch) && ch != END)
            take();
    }

    protected ParsingException error(final String message) {
        return source.error(message);
    }
    protected ParsingException error(ParsingException exception) {
        return source.error(exception);
    }

}
