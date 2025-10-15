package dev.cattyn.catformat.test.style;

import dev.cattyn.catformat.stylist.color.ColorProvider;

public record ColorHolder(int color) implements ColorProvider {
    @Override
    public int getRGB() {
        return color;
    }
}
