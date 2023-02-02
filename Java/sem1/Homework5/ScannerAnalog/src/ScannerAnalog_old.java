import java.io.*;

public class ScannerAnalog_old {
    private char[] buffer;
    final int bufferSize = 1024;
    public boolean bufferIsEmpty = true;

    private int currentPosition = 0;
    private int readed = 0;

    private Reader reader;


    public ScannerAnalog_old(InputStream input) {
        reader = new BufferedReader(new InputStreamReader(input));
        clearBuffer();
    }

    public ScannerAnalog_old(File file) throws FileNotFoundException {
        this((new FileInputStream(file)));
    }

    public ScannerAnalog_old(String string) {
        reader = new StringReader(string);
        clearBuffer();
    }

    private void clearBuffer() {
        readed = -1;

        buffer = new char[bufferSize];
        bufferIsEmpty = true;
    }

    public boolean hasNext() throws IOException {
        if (bufferIsEmpty) {
            readed = reader.read(buffer);
            bufferIsEmpty = false;
        }
        if (readed >= 0)
            return true;
        return false;
    }

    public String nextLine() throws IOException {
        StringBuilder line = new StringBuilder();
        while (buffer[currentPosition] != '\n') {
            if (currentPosition == 1024 || bufferIsEmpty) {
                readed = reader.read(buffer);

                if (readed == -1)
                    if (line.toString().isEmpty())
                        return null;
                    else
                        break;

            }

            line.append(buffer[currentPosition++]);

        }
        return line.toString();
    }

    public boolean hasNextInt() throws IOException {
        StringBuilder st = new StringBuilder();
        if (bufferIsEmpty) {
            readed = reader.read(buffer);
            bufferIsEmpty = false;
        }
        while ((Character.isWhitespace(buffer[currentPosition]) || buffer[currentPosition] == '\u0000')
                && buffer[currentPosition] != '\n') {
            if (currentPosition > 1024) {
                readed = reader.read(buffer);
                bufferIsEmpty = false;

                currentPosition = 0;
                continue;
            }
            currentPosition++;
        }

        int index = currentPosition;
        while (!Character.isWhitespace(buffer[index]) && buffer[index] != '\u0000') {
            if (bufferIsEmpty || index == 1024) {
                readed = reader.read(buffer);
                if (readed == -1)
                    return false;
                currentPosition = 0;
                index = 0;
            }
            st.append(buffer[index++]);
        }
        String temp = st.toString();
        try {
            int number = Integer.parseInt(temp);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int cnt = 0;

    public int nextInt(int length) throws IOException {
        StringBuilder st = new StringBuilder();

        while ((Character.isWhitespace(buffer[currentPosition]) || buffer[currentPosition] == '\u0000')
                && buffer[currentPosition] != '\n' && cnt < length) {
            if (currentPosition >= 1024) {
                readed = reader.read(buffer);
                bufferIsEmpty = false;

                currentPosition = 0;
                continue;
            }
            currentPosition++;
            cnt++;
        }

        while (!Character.isWhitespace(buffer[currentPosition]) && cnt < length) {
            if (buffer[currentPosition] != '\u0000') {
                st.append(buffer[currentPosition]);
            }
            currentPosition++;

            if (currentPosition == 1024) {
                readed = reader.read(buffer);
                currentPosition = 0;
                if (readed == -1)
                    break;
            }
            cnt++;
        }
        String temp = st.toString();

        return Integer.parseInt(temp);
    }

    public void close() throws IOException {
        clearBuffer();

        reader.close();
    }
}
