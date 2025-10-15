package dev.cattyn.catformat.test.style;

import dev.cattyn.catformat.stylist.color.ARGBProvider;

public record ColorHolder(int color) implements ARGBProvider {
    @Override
    public int getARGB() {
        return color;
    }
}
