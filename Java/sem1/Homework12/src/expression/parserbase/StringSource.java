package expression.parserbase;


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

}
