package md2html;

import md2html.ParagraphConverter;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Md2Html {
    public static void main(String[] args) {
        String inFile = args[0];
        String outFile = args[1];
        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(
                                        new File(inFile)), StandardCharsets.UTF_8));
                BufferedWriter out = new BufferedWriter(new FileWriter(outFile))
        ) {
            String line = in.readLine();
            while (line != null) {
                StringBuilder paragraph = new StringBuilder();
                while (line.isEmpty()) {
                    line = in.readLine();
                }
                while (line != null && !line.isEmpty()) {
                    if (paragraph.length() != 0) {
                        paragraph.append('\n');
                    }
                    paragraph.append(line);
                    line = in.readLine();
                }
                if (paragraph.length() > 0) {
                    ParagraphConverter convertedParagraph = new ParagraphConverter(paragraph.toString());
                    out.write(convertedParagraph.getResult());
                    out.newLine();
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }
}