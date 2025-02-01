package dev.cattyn.catformat.parser;

import dev.cattyn.catformat.entry.EntryContainer;

public class HexParser implements Parser {
    @Override
    public int getColor(EntryContainer entries, String expr) {
        if (expr.length() == 3) {
            // css color support
            char[] c = expr.toCharArray();
            String hex = "" + c[0] + c[0] + c[1] + c[1] + c[2] + c[2];
            return Integer.parseInt(hex, 16);
        }
        return Integer.parseInt(expr, 16);
    }
}
