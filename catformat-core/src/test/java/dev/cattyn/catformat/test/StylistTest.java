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
        assertEquals(format.entries().get("MAGENTA_LIGHT_COLOR").getColor(), Color.magenta.brighter().hashCode());
        assertEquals(format.entries().get("black").getColor(), Color.black.hashCode());

        assertEquals(format.entries().get("dynamic").getColor(), Color.blue.hashCode());
        setDynamicToGreen.run();
        assertEquals(format.entries().get("dynamic").getColor(), Color.green.hashCode());

        assertEquals(format.entries().get("theme-color").getColor(), Color.black.hashCode());
        enableDarkMode.run();
        assertEquals(format.entries().get("theme-color").getColor(), Color.white.hashCode());
    }
}
