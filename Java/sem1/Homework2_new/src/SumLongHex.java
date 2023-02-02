import static java.lang.Math.min;

public class SumLongHex {
    public static int findEndOfNumb(int i, String st) {
        while (i < st.length() && !Character.isWhitespace(st.charAt(i))) {
            ++i;
        }
        return i;
    }

    public static long getNumb(String numbString) {
        if (numbString.startsWith("0x") || numbString.startsWith("0X")) {
            return Long.parseUnsignedLong(numbString.substring(2), 16);
        } else {
            return Long.parseLong(numbString);
        }
    }

    public static void main(String[] args) {
        long answer = 0;

        for (String st : args) {
            int stringStart = 0;

            for (int i = 0; i < st.length(); ++i) {
                if (!Character.isWhitespace(st.charAt(i))) {
                    stringStart = i;

                    i = findEndOfNumb(i, st);
                    answer += getNumb(st.substring(stringStart, i));
                }
            }
        }
        System.out.println(answer);
    }
}