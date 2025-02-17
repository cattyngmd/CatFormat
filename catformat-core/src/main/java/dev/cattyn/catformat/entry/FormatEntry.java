package dev.cattyn.catformat.entry;

import java.util.function.Supplier;

public record FormatEntry(String name, Supplier<Integer> color) {
    public int getColor() {
        return color.get();
    }
}
