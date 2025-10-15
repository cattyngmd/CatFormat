package dev.cattyn.catformat.test.style;

import dev.cattyn.catformat.stylist.annotations.Style;

import java.awt.*;

public class SimpleStyle {
    public static boolean DARK_MODE = false;

    @Style
    static final int MAGENTA_LIGHT_COLOR = Color.magenta.brighter().hashCode();

    @Style("black")
    static final Color BLACK_COLOR = Color.black;

    @Style("dynamic")
    public static int DYNAMIC = Color.blue.hashCode();

    @Style("theme-color")
    static int getTextColor() {
        if (DARK_MODE) return Color.white.hashCode();
        return Color.black.hashCode();
    }

    @Style("background-color")
    static ColorHolder getBackgroundColor() {
        return new ColorHolder(-1);
    }
}
