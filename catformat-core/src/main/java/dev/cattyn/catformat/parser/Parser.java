package dev.cattyn.catformat.parser;

import dev.cattyn.catformat.entry.EntryContainer;

public interface Parser {
    int getColor(EntryContainer entries, String expr);
}
