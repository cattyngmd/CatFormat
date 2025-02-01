package dev.cattyn.catformat;

import dev.cattyn.catformat.entry.EntryContainer;
import dev.cattyn.catformat.entry.FormatEntry;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface CatFormat<T> {
    EntryContainer entries();

    T format(String s);

    default T format(String s, Object... o) {
        return format(s.formatted(o));
    }

    default CatFormat<T> styled(Consumer<EntryContainer> consumer) {
        consumer.accept(entries());
        return this;
    }

    default CatFormat<T> add(String name, Supplier<Integer> color) {
        entries().add(new FormatEntry(name, color));
        return this;
    }

    default CatFormat<T> add(Object o) {
        EntryContainer entries = entries();
        entries.stylist().getEntries0(o).forEach(entries::add);
        return this;
    }

    default CatFormat<T> add(String name, int color) {
        return add(name, () -> color);
    }
}
