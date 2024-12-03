package dev.cattyn.catformat.parsers;

import dev.cattyn.catformat.CatFormat;

public class HexParser implements Parser {
    @Override
    public int getColor(CatFormat<?> format, String expr) {
        if (expr.length() == 3) {
            // css color support
            char[] c = expr.toCharArray();
            String hex = "" + c[0] + c[0] + c[1] + c[1] + c[2] + c[2];
            return Integer.parseInt(hex, 16);
        }
        return Integer.parseInt(expr, 16);
    }
}
