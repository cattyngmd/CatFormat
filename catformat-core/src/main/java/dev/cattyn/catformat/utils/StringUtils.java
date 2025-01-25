package dev.cattyn.catformat.utils;

public class StringUtils {
    private StringUtils() {
        throw new AssertionError();
    }

    public static void clear(StringBuilder sb) {
        sb.setLength(0);
    }

    public static void shrink(StringBuilder sb) {
        sb.setLength(sb.length() - 1);
    }
}
