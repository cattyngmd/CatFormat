package dev.cattyn.catformat.stylist;

import dev.cattyn.catformat.entry.FormatEntry;
import dev.cattyn.catformat.stylist.wrappers.AwtWrapper;
import dev.cattyn.catformat.stylist.wrappers.ColorWrapper;

import java.util.ArrayList;
import java.util.List;

public interface Stylist<T> {
    // TODO no. This way it sucks...
    List<ColorWrapper<?>> WRAPPERS = new ArrayList<>(List.of(new AwtWrapper()));

    List<FormatEntry> getEntries(T target);

    default List<FormatEntry> getEntries0(Object object) {
        return getEntries((T) object);
    }
}
