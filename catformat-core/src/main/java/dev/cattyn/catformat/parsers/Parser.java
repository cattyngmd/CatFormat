package dev.cattyn.catformat.parsers;

import dev.cattyn.catformat.CatFormat;

public interface Parser {
    int getColor(CatFormat<?> format, String expr);
}
