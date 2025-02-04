package dev.cattyn.catformat.test.style;

import dev.cattyn.catformat.stylist.annotations.Style;

import java.awt.*;

public class NonStaticStyle {
    public boolean darkMode = false;

    @Style
    int MAGENTA_LIGHT_COLOR = Color.magenta.brighter().hashCode();

    @Style("black")
    Color BLACK_COLOR = Color.black;

    @Style("dynamic")
    public int dynamic = Color.blue.hashCode();

    @Style("theme-color")
    int getTextColor() {
        if (darkMode) return Color.white.hashCode();
        return Color.black.hashCode();
    }
}
