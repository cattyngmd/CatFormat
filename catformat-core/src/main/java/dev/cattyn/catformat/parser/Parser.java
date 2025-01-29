package dev.cattyn.catformat.parser;

import dev.cattyn.catformat.CatFormat;

public interface Parser {
    int getColor(CatFormat<?> format, String expr);
}
