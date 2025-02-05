package dev.cattyn.catformat.stylist.impl;

import dev.cattyn.catformat.entry.FormatEntry;
import dev.cattyn.catformat.stylist.ColorStylist;
import dev.cattyn.catformat.stylist.impl.members.FieldStylist;
import dev.cattyn.catformat.stylist.impl.members.MethodStylist;
import dev.cattyn.catformat.stylist.wrappers.ColorWrapper;

import java.util.ArrayList;
import java.util.List;

public class ClassStylist implements ColorStylist<Object> {
    private final List<ColorWrapper<?>> wrappers = new ArrayList<>();

    @Override
    public List<FormatEntry> getEntries(Object target) {
        List<FormatEntry> entries = new ArrayList<>();
        entries.addAll(methods(target));
        entries.addAll(fields(target));
        return entries;
    }

    private List<FormatEntry> methods(Object o) {
        return new MethodStylist(this, getInstance(o))
                .getEntries(getClass(o).getDeclaredMethods());
    }

    private List<FormatEntry> fields(Object o) {
        return new FieldStylist(this, getInstance(o))
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

    @Override
    public void addColor(ColorWrapper<?> color) {
        wrappers.add(color);
    }

    @Override
    public ColorWrapper<?> getColor(Class<?> klass) {
        for (ColorWrapper<?> wrapper : wrappers) {
            if (wrapper.getType().isAssignableFrom(klass))
                return wrapper;
        }
        throw new IllegalArgumentException("Color wrapper for class %s not found!".formatted(klass));
    }
}
