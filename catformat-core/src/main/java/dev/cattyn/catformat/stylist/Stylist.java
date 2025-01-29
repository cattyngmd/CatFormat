package dev.cattyn.catformat.stylist;

import dev.cattyn.catformat.formatter.FormatEntry;

import java.util.List;

public interface Stylist<T> {
    List<FormatEntry> getEntries(T target);

    default List<FormatEntry> getEntries0(Object object) {
        return getEntries((T) object);
    }
}
