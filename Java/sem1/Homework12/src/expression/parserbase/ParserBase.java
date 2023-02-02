package expression.parserbase;

public class ParserBase {
    protected static final char END = 0;
    private final CharSource source;
    private char ch;

    public ParserBase(CharSource source) {
        this.source = source;
        ch = source.next();
    }

    protected char current() {
        return ch;
    }

    protected boolean test(final char expected) {
        return ch == expected;
    }

    protected boolean testAnyFromString(final String expected) {
        for (final char c : expected.toCharArray()) {
            if (test(c))
                return true;
        }
        return false;
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

    protected boolean expect(final char expected) {
        return take(expected);
    }

    protected void expect(final String expected) {
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
/*        while (take(' ')
                || take('\n')
                || take('\r')
                || take('\t')
                || take('\f')
        );

 */

        while (Character.isWhitespace(ch) && ch != END)
            take();
    }

}
