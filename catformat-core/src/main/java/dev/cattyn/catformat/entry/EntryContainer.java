package dev.cattyn.catformat.entry;

import dev.cattyn.catformat.stylist.Stylist;

public interface EntryContainer {
    FormatEntry NULL_FORMAT = new FormatEntry("null", () -> -1);

    void add(FormatEntry entry);

    FormatEntry get(String name);

    Stylist<?> stylist();

    void stylist(Stylist<?> stylist);
}
