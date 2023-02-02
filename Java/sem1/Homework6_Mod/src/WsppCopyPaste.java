import java.io.*;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class WsppCopyPaste {
    public static boolean checkChar(char a) {
        if (Character.isLetter(a) || Character.getType(a) == Character.DASH_PUNCTUATION || a == '\'')
            return false;
        return true;
    }

    public static void main(String[] args) {
        try {
            String inputFileName = args[0];
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(inputFileName),
                            "utf8"
                    )
            );

            Map<String, IntList> words = new LinkedHashMap<>();
            int index = 0;
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
                words.put(s, words.getOrDefault(s, new IntList(1)));
                words.get(s).plus(0, 1);
                words.get(s).pushBack(index + 1);
                index++;
            }

            reader.close();

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(args[1]),
                            "utf8")
            );


            try {
                for (String entry : words.keySet()) {
                    writer.write(entry + " ");
                    for (int i = 0; i < words.get(entry).size(); i++) {
                        if (i == words.get(entry).size() - 1) {
                            writer.write(words.get(entry).get(i) + System.lineSeparator());
                        } else {
                            writer.write(words.get(entry).get(i) + " ");
                        }
                    }

                }
            } finally {
                writer.close();
            }

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.err.println("File Not Found");
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}