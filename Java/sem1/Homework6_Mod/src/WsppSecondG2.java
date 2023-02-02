import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class WsppSecondG2 {
    public static void main(String[] args) {
        Map<String, IntList> words = new LinkedHashMap<>();
        //0 index in key is count in all file, 1 index in key is count in line

        try {
            File file = new File(args[0]);
            MyScanner reader = new MyScanner(file);

            try {
                int index = 0;
                while (reader.bufferIsFilled()) {
                    String s = reader.nextWord();

                    if (s.equals("\n")) {
                        for (String entry : words.keySet()) {
                            words.get(entry).plus(1, -words.get(entry).get(1));//set 0 count words in line
                        }
                        continue;
                    }

                    if (s.endsWith("\n")) {
                        for (String entry : words.keySet()) {
                            words.get(entry).plus(1, -words.get(entry).get(1));//set 0 count words in line
                        }
                        s = s.substring(0, s.length() - 1);
                    }

                    /*if (s.isEmpty()) { //err
                        continue;
                    }*/

                    s = s.toLowerCase();
                    words.put(s, words.getOrDefault(s, new IntList(2)));
                    words.get(s).plus(0, 1);
                    words.get(s).plus(1, 1);
                    if ((words.get(s).get(1)) % 2 == 0) {
                        words.get(s).pushBack(index + 1);
                    }
                    index++;
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
                for (String entry : words.keySet()) {
                    writer.write(entry);
                    for (int i = 0; i < words.get(entry).size(); i++) { //err
                        if (i == 1) {
                            continue;
                        }

                        writer.write(" " + words.get(entry).get(i));

                    }
                    writer.write(System.lineSeparator());
                }
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("File can't be written " + e.getMessage());
        }
    }
}



