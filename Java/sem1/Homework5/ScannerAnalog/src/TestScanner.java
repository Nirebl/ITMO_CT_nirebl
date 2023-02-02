import java.io.*;
import java.util.InputMismatchException;

public class TestScanner implements AutoCloseable {
    private Reader in;
    char[] buffer = new char[1024];
    private int amount = 0;
    private int pos = 0;

    public TestScanner(InputStream input) throws IOException {
        this.in = new InputStreamReader(input);
    }

    public TestScanner(String string) throws IOException {
        this.in = new StringReader(string);
    }

    private boolean fillBuffer() throws IOException {
        if (pos == amount) {
            amount = in.read();
            pos = 0;
        }
        return amount != -1;
    }

    public boolean hasNext() throws IOException {
        return fillBuffer();
    }

    public String nextLine() throws IOException {
        StringBuilder str = new StringBuilder();
        while (fillBuffer() && buffer[pos] != '\n') {
            str.append(buffer[pos++]);
        }
        pos++;
        return str.toString();
    }

    public String next() throws IOException {
        StringBuilder str = new StringBuilder();
        while(fillBuffer() && Character.isWhitespace(buffer[pos])) {
            pos++;
        }
        while (fillBuffer() && !Character.isWhitespace(buffer[pos])) {
            str.append(buffer[pos++]);
        }
        return str.toString();
    }

    public void close() throws IOException {
        in.close();
    }
}
