package dev.cattyn.catformat.parsers;

import dev.cattyn.catformat.CatFormat;

public class NameParser implements Parser {
    @Override
    public int getColor(CatFormat<?> format, String expr) {
        return format.getEntry(expr).getColor();
    }
}
