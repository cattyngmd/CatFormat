package dev.cattyn.catformat;

import dev.cattyn.catformat.entry.EntryContainer;
import dev.cattyn.catformat.entry.EntryContainerImpl;
import dev.cattyn.catformat.text.TextWrapper;

public class CatFormatImpl<T> implements CatFormat<T> {
    private final EntryContainer container = new EntryContainerImpl();
    private final TextWrapper<T> wrapper;

    public CatFormatImpl(TextWrapper<T> wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public EntryContainer entries() {
        return container;
    }

    @Override
    public TextWrapper<T> wrapper() {
        return wrapper;
    }

    public T format(String s) {
        return new Formatter<>(this, s).handle();
    }
}
