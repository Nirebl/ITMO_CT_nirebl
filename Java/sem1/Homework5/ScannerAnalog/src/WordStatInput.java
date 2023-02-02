/*import java.io.*;
import java.util.*;

public class WordStatInput {
    public static boolean checkChar(char a) {
        if (Character.isLetter(a) || Character.getType(a) == Character.DASH_PUNCTUATION || a == '\'')
            return false;
        return true;
    }

    public static void main(String[] args) {
        String inputFileName = args[0];
        try {
            ScannerAnalog reader = new ScannerAnalog(inputFileName, 1);

            Map<String, Integer> words = new LinkedHashMap<>();

            while (reader.bufferIsFilled()) {
                String s = reader.nextLine();

                for (int i = 0; i < s.length(); i++) {
                    if (checkChar(s.charAt(i))) {
                        continue;
                    }

                    int start = i;
                    while (i < s.length() && !checkChar(s.charAt(i))) {
                        i++;
                    }

                    String word = s.substring(start, i).toLowerCase(Locale.ROOT);

                    if (words.containsKey(word)) {
                        words.replace(word,
                                words.get(word) + 1);
                    } else {
                        words.put(word, 1);
                    }


                }
            }
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(args[1]),
                            "utf8")
            );
            for (String keys : words.keySet()) {
                //writer.append(keys).append(" ").append(String.valueOf(words.get(keys))).append('\n');
                writer.write(keys + " " + words.get(keys) + '\n');
            }
            writer.close();
            reader.close();

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.err.println("File Not Found");
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }


        //Scanner fileIn = new Scanner(System.in);


    }
}*/