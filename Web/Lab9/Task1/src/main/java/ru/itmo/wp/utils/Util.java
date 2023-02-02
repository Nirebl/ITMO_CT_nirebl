package ru.itmo.wp.utils;

public class Util {

    public static long parseStrToLong(String str) {
        long res = -1;
        try {
            res = Long.parseLong(str);
        } catch (NumberFormatException ignored) { }

        return res;
    }

}
