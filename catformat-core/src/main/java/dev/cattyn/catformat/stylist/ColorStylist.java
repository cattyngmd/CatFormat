package dev.cattyn.catformat.stylist;

import dev.cattyn.catformat.stylist.wrappers.ColorWrapper;

public interface ColorStylist<T> extends Stylist<T> {
    void addColor(ColorWrapper<?> color);

    ColorWrapper<?> getColor(Class<?> klass);
}
