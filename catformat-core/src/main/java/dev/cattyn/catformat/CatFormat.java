package dev.cattyn.catformat;

import dev.cattyn.catformat.text.TextWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CatFormat<T> {
    private static final FormatEntry NULL_FORMAT = new FormatEntry("null", () -> -1);
    private final List<FormatEntry> formats = new ArrayList<>();
    private final TextWrapper<T> wrapper;

    public CatFormat(TextWrapper<T> wrapper) {
        this.wrapper = wrapper;
    }

    public FormatEntry getEntry(String name) {
        for (FormatEntry format : formats) {
            if (format.name().equalsIgnoreCase(name))
                return format;
        }
        return NULL_FORMAT;
    }

    public CatFormat<T> add(String name, int color) {
        return add(name, () -> color);
    }

    public CatFormat<T> add(String name, Supplier<Integer> color) {
        formats.add(new FormatEntry(name, color));
        return this;
    }

    public T format(String s, Object... objects) {
        return format(s.formatted(objects));
    }

    public T format(String s) {
        return new Formatter<>(this, s).handle();
    }

    public TextWrapper<T> getWrapper() {
        return wrapper;
    }
}
