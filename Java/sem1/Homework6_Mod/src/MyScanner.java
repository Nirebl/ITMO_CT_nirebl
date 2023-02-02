import java.io.*;
import java.nio.charset.StandardCharsets;

public class MyScanner {
    private Reader reader;
    private int readed = 0;

    private char[] buffer = new char[1024];
    private int currentPosition = 0;
    public int currentLine = 0;

    public MyScanner(InputStream input) {
        reader = new InputStreamReader(input);
    }


    public MyScanner(String string) {
        reader = new StringReader(string);
    }

    public MyScanner(File fileName) throws IOException {
        reader = new FileReader(fileName, StandardCharsets.UTF_8);
    }

    public boolean bufferIsFilled() throws IOException {
        if (currentPosition == readed) {
            readed = reader.read(buffer);
            currentPosition = 0;
        }
        return readed != -1;
    }

    private void skipWhiteSpacesExceptEOL() throws IOException {
        while (bufferIsFilled() && Character.isWhitespace(buffer[currentPosition]) && buffer[currentPosition] != '\n'
                && buffer[currentPosition] != '\r') {
            if (buffer[currentPosition] == '\n' || buffer[currentPosition] == '\r') {
                currentLine++;
            }
            currentPosition++;
        }
    }

    private static boolean checkChar(char a) {
        return Character.isLetter(a) || Character.getType(a) == Character.DASH_PUNCTUATION || a == '\'';
    }

    public String nextWord() throws IOException {
        StringBuilder temp = new StringBuilder();

        while (bufferIsFilled() && (!checkChar(buffer[currentPosition])
                || Character.isWhitespace(buffer[currentPosition]) || buffer[currentPosition] == '\n'
                || buffer[currentPosition] == '\r')) {
            if (buffer[currentPosition] == '\n' || buffer[currentPosition] == '\r') {
                currentLine++;
            }
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

    public void close() throws IOException {
        reader.close();
    }
}
