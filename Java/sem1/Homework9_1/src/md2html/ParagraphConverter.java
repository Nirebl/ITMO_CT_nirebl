package md2html;

import java.util.Map;

public class ParagraphConverter {
    private final Map<String, String> md2htmlTags = Map.of(
            "*", "em",
            "_", "em",
            "**", "strong",
            "__", "strong",
            "`", "code",
            "--", "s",
            "```", "pre");

    private final Map<Character, String> htmlSymbols = Map.of(
            '<', "&lt;",
            '>', "&gt;",
            '&', "&amp;");

    private int index;
    private StringBuilder resultLine;

    public ParagraphConverter(String paragraph) {
        resultLine = new StringBuilder();
        index = 0;
        final int headerLevel = getHeaderLevel(paragraph);
        if (headerLevel > 0) {
            resultLine.append("<h").append(headerLevel).append(">");
            index = headerLevel + 1;
        } else {
            resultLine.append("<p>");
        }
        nextTag(paragraph, resultLine, "");
        if (headerLevel > 0) {
            resultLine.append("</h").append(headerLevel).append(">");
        } else {
            resultLine.append("</p>");
        }
    }

    public String getResult() {
        return resultLine.toString();
    }

    private int getHeaderLevel(String line) {
        int headerLevel = 0;
        while (headerLevel < line.length() && line.charAt(headerLevel) == '#') {
            headerLevel++;
        }
        if (headerLevel < line.length() && line.charAt(headerLevel) == ' ') {
            return headerLevel;
        }
        return 0;
    }

    private StringBuilder nextTag(String line, StringBuilder resLine, String lastTag) {
        String markdownTag = "";
        String htmlTag = "";
        while (index < line.length()) {
            char curChar = line.charAt(index);
            switch (curChar) {
                case '`':
                    if (line.length() > index + 2 && line.substring(index, index + 3).equals("```")) {
                        markdownTag = "```";
                        index += 2;
                    } else {
                        markdownTag = Character.toString(curChar);
                    }
                    htmlTag = md2htmlTags.get(markdownTag);
                    break;
                case '*':
                case '_':
                    if (index + 1 < line.length() && !lastTag.equals(Character.toString(curChar)) &
                            line.charAt(index + 1) == curChar) {
                        markdownTag = line.substring(index, index + 2);
                        index++;
                    } else {
                        markdownTag = line.substring(index, index + 1);
                    }
                    htmlTag = md2htmlTags.get(markdownTag);
                    break;
                case '\\':
                    if (index + 1 < line.length() &&
                            md2htmlTags.get(Character.toString(line.charAt(index + 1))) != null) {
                        index++;
                    }
                    resLine.append(line.charAt(index));
                    break;
                case '-':
                    if (index + 1 < line.length()
                            && line.charAt(index + 1) == '-') {
                        markdownTag = "--";
                        index++;
                        htmlTag = md2htmlTags.get(markdownTag);
                        break;
                    }
                default:
                    String htmlSymbol = htmlSymbols.getOrDefault(curChar, String.valueOf(curChar));
                    resLine.append(htmlSymbol);
            }

            if (!markdownTag.isEmpty() && markdownTag.equals(lastTag)) {  // close last tag
                resLine.append("</").append(htmlTag).append(">");
                return resLine;
            }
            index++;

            // parse code without format
            if (markdownTag.equals("```") && index < line.length()) {
                int stInd = index;
                StringBuilder strToAdd = new StringBuilder();

                while (stInd < line.length()) {
                    if (line.charAt(stInd) == '`') {
                        if (stInd + 2 < line.length() && line.substring(stInd, stInd + 3).equals("```")) {
                            index = stInd + 3;
                            break;
                        }
                    }
                    strToAdd.append(line.charAt(stInd));
                    stInd++;

                }
                resLine.append('<').append(htmlTag).append('>');
                resLine.append(strToAdd).append("</").append(htmlTag).append('>');
                markdownTag = "";
            }

            if (!markdownTag.isEmpty()) { // parse after tag
                StringBuilder editedLine = nextTag(line, new StringBuilder(), markdownTag);
                if (editedLine.length() > htmlTag.length() &&  // check closing tag
                        editedLine.substring(editedLine.length() - htmlTag.length() - 1,
                                editedLine.length() - 1).equals(htmlTag)) {
                    resLine.append("<").append(htmlTag).append(">").append(editedLine);
                    index++;
                } else {
                    if (!markdownTag.equals("[")) {
                        resLine.append(markdownTag);
                    }
                    resLine.append(editedLine);
                }
                markdownTag = "";
            }
        }
        return resLine;
    }
}
