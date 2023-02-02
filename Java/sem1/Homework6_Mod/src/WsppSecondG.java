import java.io.*;
import java.util.*;

public class WsppSecondG {
    public static void main(String[] args) {
        Map<String, IntList> words = new LinkedHashMap<>();
        /*
        0 index in key is last index of line where word was found
        1 index in key is count in line
        2 index in key is count in all file
        */

        try {
            File file = new File(args[0]);
            MyScanner reader = new MyScanner(file);

            try {
                int index = 0;

                String s = reader.nextWord();

                while (reader.bufferIsFilled()) {
                    s = s.toLowerCase();

                    words.put(s, words.getOrDefault(s, new IntList(3)));

                    if (words.get(s).get(0) != reader.currentLine) {
                        words.get(s).set(1, 0);
                        words.get(s).set(0, reader.currentLine);
                    }

                    words.get(s).plus(1, 1);
                    words.get(s).plus(2, 1);


                    if ((words.get(s).get(1)) % 2 == 0) {
                        words.get(s).pushBack(index + 1);
                    }

                    index++;
                    s = reader.nextWord();

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
                for (Map.Entry<String, IntList> entry : words.entrySet()) {
                    String word = entry.getKey();
                    IntList list = entry.getValue();
                    writer.write(word);
                    for (int i = 2; i < list.size(); i++) {
                        writer.write(" " + list.get(i));
                    }
                    writer.newLine();
                }
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("File can't be written " + e.getMessage());
        }
    }
}



