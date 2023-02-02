import java.io.*;
import java.util.*;


public class WordStatWords {
    public static boolean checkChar(char a) {
        return !Character.isLetter(a) && Character.getType(a) != Character.DASH_PUNCTUATION && a != '\'';
    }

    public static void main(String[] args) {
        Map<String, Integer> words = new TreeMap<>();
        try {
            String inputFileName = args[0];
            File file = new File(inputFileName);
            ScannerAnalog reader = new ScannerAnalog(file);
            try {
                while (true) {
                    int ch = reader.read();
                    while (Character.isWhitespace(ch) && ch != -1) {
                        ch = reader.read();
                    }

                    StringBuilder temp = new StringBuilder();

                    while (ch != -1 && !checkChar((char) ch)) {
                        temp.append((char) ch);
                        ch = reader.read();
                    }
                    if (ch == -1) {
                        break;
                    }

                    String s = temp.toString();

                    if (s.isEmpty())
                        continue;

                    s = s.toLowerCase(Locale.ROOT);
                    words.put(s, words.getOrDefault(s, 0) + 1);
                }
            } finally {
                reader.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Can't open file " + e.getMessage());
        } catch (IOException e) {
            System.out.println("File can't be read " + e.getMessage());
        }

        try {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(args[1]),
                            "utf8")
            );
            try {
                for (Map.Entry<String, Integer> entry : words.entrySet()) {
                    writer.write(entry.getKey() + " " + entry.getValue() + System.lineSeparator());
                }
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("File can't be written " + e.getMessage());
        }
    }
}



