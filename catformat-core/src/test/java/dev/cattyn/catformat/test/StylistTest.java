package dev.cattyn.catformat.test;

import dev.cattyn.catformat.CatFormat;
import dev.cattyn.catformat.CatFormatImpl;
import dev.cattyn.catformat.test.content.ContentWrapper;
import dev.cattyn.catformat.test.style.IllegalStyle;
import dev.cattyn.catformat.test.style.NonStaticStyle;
import dev.cattyn.catformat.test.style.SimpleStyle;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StylistTest {
    @Test
    public void illegalStyleTest() {
        CatFormat<String> format = new CatFormatImpl<>(new ContentWrapper());
        assertThrows(IllegalArgumentException.class,
                () -> format.add(new IllegalStyle()));
    }

    @Test
    public void stylistTest() {
        CatFormat<String> format = new CatFormatImpl<>(new ContentWrapper());
        format.add(SimpleStyle.class);
        assertStyle(
                format,
                () -> SimpleStyle.DYNAMIC = Color.green.hashCode(),
                () -> SimpleStyle.DARK_MODE = true
        );
    }

    @Test
    public void stylistNonStaticTest() {
        NonStaticStyle style = new NonStaticStyle();
        CatFormat<String> format = new CatFormatImpl<>(new ContentWrapper());
        format.add(style);
        assertStyle(
                format,
                () -> style.dynamic = Color.green.hashCode(),
                () -> style.darkMode = true
        );
    }

    // maybe its autistic But I don't really Care.
    private void assertStyle(CatFormat<String> format, Runnable setDynamicToGreen, Runnable enableDarkMode) {
        assertEquals(Color.magenta.brighter().hashCode(), format.entries().get("MAGENTA_LIGHT_COLOR").getColor());
        assertEquals(Color.black.hashCode(), format.entries().get("black").getColor());

        assertEquals(Color.blue.hashCode(), format.entries().get("dynamic").getColor());
        setDynamicToGreen.run();
        assertEquals(Color.green.hashCode(), format.entries().get("dynamic").getColor());

        assertEquals(Color.black.hashCode(), format.entries().get("theme-color").getColor());
        enableDarkMode.run();
        assertEquals(Color.white.hashCode(), format.entries().get("theme-color").getColor());

        assertEquals(-1, format.entries().get("background-color").getColor());
    }
}
