package expression.parserimpl;

import expression.parserimpl.exception.ExpressionException;

public class StringSource implements CharSource {
    private final String data;
    private int pos;

    public StringSource(String data) {
        this.data = data;
        this.pos = 0;
    }

    @Override
    public boolean hasNext() {
        return pos < data.length();
    }

    @Override
    public char next() {
        return data.charAt(pos++);
    }


    @Override
    public ExpressionException error(String message) {
        return new ExpressionException(String.format(
                "%d: %s (rest of input: %s)",
                pos, message, data.substring(pos)));
    }

}
