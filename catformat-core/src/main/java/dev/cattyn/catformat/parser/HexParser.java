package dev.cattyn.catformat.parser;

import dev.cattyn.catformat.entry.EntryContainer;

public class HexParser implements Parser {
    @Override
    public int getColor(EntryContainer entries, String expr) {
        if (expr.length() == 3) {
            int r = Character.digit(expr.charAt(0), 16);
            int g = Character.digit(expr.charAt(1), 16);
            int b = Character.digit(expr.charAt(2), 16);

            // r = 0xf
            // r << 4 = 0xf0
            // r << 4 | r = 0xff
            return (r << 4 | r) << 16 | (g << 4 | g) << 8 | (b << 4 | b);
        }
        return Integer.parseInt(expr, 16);
    }
}
