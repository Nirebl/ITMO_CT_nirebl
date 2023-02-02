import java.io.*;
import java.nio.charset.StandardCharsets;

public class ScannerAnalog {
    private Reader reader;
    private int readed = 0;

    private char[] buffer = new char[1024];
    private int currentPosition = 0;

    public ScannerAnalog(InputStream input) {
        reader = new InputStreamReader(input);
    }


    public ScannerAnalog(String string) {
        reader = new StringReader(string);
    }

    public ScannerAnalog(File fileName) throws IOException {
        reader = new FileReader(fileName, StandardCharsets.UTF_8);
    }

    public boolean bufferIsFilled() throws IOException {
        if (currentPosition == readed) {
            readed = reader.read(buffer);
            currentPosition = 0;
        }
        return readed != -1;
    }

    private static boolean checkChar(char a) {
        return Character.isLetter(a) || Character.getType(a) == Character.DASH_PUNCTUATION || a == '\'';
    }

    public String nextWord() throws IOException {
        StringBuilder temp = new StringBuilder();

        while (bufferIsFilled() && (!checkChar(buffer[currentPosition]) || buffer[currentPosition] == '\n'
                || buffer[currentPosition] == '\r'
                || Character.isWhitespace(buffer[currentPosition]))) {
            currentPosition++;
        }

        while (bufferIsFilled() && buffer[currentPosition] != '\n' && buffer[currentPosition] != '\r'
                && !Character.isWhitespace(buffer[currentPosition])) {
            if (checkChar(buffer[currentPosition])) {
                temp.append(buffer[currentPosition]);
                currentPosition++;
            } else {
                break;
            }
        }
        return temp.toString();

    }

    public String split() throws IOException {
        StringBuilder temp = new StringBuilder();

        while (bufferIsFilled() && buffer[currentPosition] == '\n' || buffer[currentPosition] == '\r'
                || Character.isWhitespace(buffer[currentPosition])) {
            currentPosition++;
        }

        while (bufferIsFilled() && buffer[currentPosition] != '\n' && buffer[currentPosition] != '\r'
                && !Character.isWhitespace(buffer[currentPosition])) {
            temp.append(buffer[currentPosition]);
            currentPosition++;
        }
        currentPosition++;
        return temp.toString();
    }

    public String nextLine() throws IOException {
        StringBuilder temp = new StringBuilder();

        while (bufferIsFilled() && buffer[currentPosition] != '\n') {
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
