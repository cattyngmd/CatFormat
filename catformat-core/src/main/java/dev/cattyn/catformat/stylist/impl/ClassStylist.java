package dev.cattyn.catformat.stylist.impl;

import dev.cattyn.catformat.entry.FormatEntry;
import dev.cattyn.catformat.stylist.Stylist;
import dev.cattyn.catformat.stylist.impl.members.FieldStylist;
import dev.cattyn.catformat.stylist.impl.members.MethodStylist;

import java.util.ArrayList;
import java.util.List;

public class ClassStylist implements Stylist<Object> {
    @Override
    public List<FormatEntry> getEntries(Object target) {
        List<FormatEntry> entries = new ArrayList<>();
        entries.addAll(methods(target));
        entries.addAll(fields(target));
        return entries;
    }

    private List<FormatEntry> methods(Object o) {
        return new MethodStylist(getInstance(o))
                .getEntries(getClass(o).getDeclaredMethods());
    }

    private List<FormatEntry> fields(Object o) {
        return new FieldStylist(getInstance(o))
                .getEntries(getClass(o).getDeclaredFields());
    }

    private Object getInstance(Object target) {
        if (target instanceof Class<?>) return null;
        return target;
    }

    private Class<?> getClass(Object o) {
        if (o instanceof Class<?> klass) return klass;
        return o.getClass();
    }
}
