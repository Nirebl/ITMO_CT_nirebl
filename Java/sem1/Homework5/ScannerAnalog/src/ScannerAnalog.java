import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ScannerAnalog {
    private Reader reader;
    private int readed = 0;

    private char[] buffer = new char[1024];
    private int currentPosition = 0;

    public ScannerAnalog(InputStream input) {
        reader = new BufferedReader(new InputStreamReader(input));
    }

    public ScannerAnalog(File file) throws FileNotFoundException, UnsupportedEncodingException {

        reader = new FileReader(file);
    }

    public ScannerAnalog(String string) {
        reader = new StringReader(string);
    }

    public boolean bufferIsFilled() throws IOException {
        if (currentPosition == readed) {
            readed = reader.read(buffer);
            currentPosition = 0;
        }
        return readed != -1;
    }

    public int read() throws IOException {
        if (bufferIsFilled()) {
            currentPosition++;
            return buffer[currentPosition - 1];
        } else {
            return -1;
        }
    }

    public String split() throws IOException {
        StringBuilder temp = new StringBuilder();
        while (bufferIsFilled() && (buffer[currentPosition] != '\n' || buffer[currentPosition] != '\r')
                && !Character.isWhitespace(buffer[currentPosition])) {
            temp.append(buffer[currentPosition]);
            currentPosition++;
        }
        currentPosition++;
        return temp.toString();
    }

    public String nextLine() throws IOException {
        StringBuilder temp = new StringBuilder();

        while (bufferIsFilled() && (buffer[currentPosition] != '\n' || buffer[currentPosition] != '\r')) {
            temp.append(buffer[currentPosition]);
            currentPosition++;
        }
        currentPosition++;
        return temp.toString();
    }


    public void close() throws IOException {
        reader.close();
    }
}
