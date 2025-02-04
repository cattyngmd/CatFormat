package dev.cattyn.catformat.parser;

import dev.cattyn.catformat.entry.EntryContainer;

public class NameParser implements Parser {
    @Override
    public int getColor(EntryContainer entries, String expr) {
        return entries.get(expr).getColor();
    }
}
