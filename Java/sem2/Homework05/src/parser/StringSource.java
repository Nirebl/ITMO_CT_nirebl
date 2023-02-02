package parser;

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
    public void back() {
        pos--;
    }


    @Override
    public ParsingException error(String message) {
        return new ParsingException(String.format(
                "%d: %s (rest of input: %s)",
                pos, message, data.substring(pos)));
    }

    @Override
    public ParsingException error(ParsingException exception) {
        return new ParsingException(String.format(
                "%d: (rest of input: %s)",
                pos, data.substring(pos)), exception);
    }

}
