package dev.cattyn.catformat.entry;

import dev.cattyn.catformat.stylist.Stylist;
import dev.cattyn.catformat.stylist.impl.ClassStylist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntryContainerImpl implements EntryContainer {
    private final List<FormatEntry> formats = Collections.synchronizedList(new ArrayList<>());
    private Stylist<?> stylist = new ClassStylist();

    @Override
    public void add(FormatEntry entry) {
        formats.add(entry);
    }

    @Override
    public FormatEntry get(String name) {
        return formats.stream()
                .filter(f -> f.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(NULL_FORMAT);
    }

    @Override
    public Stylist<?> stylist() {
        return stylist;
    }

    @Override
    public void stylist(Stylist<?> stylist) {
        this.stylist = stylist;
    }
}
