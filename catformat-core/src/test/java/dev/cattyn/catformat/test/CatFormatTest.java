package dev.cattyn.catformat.test;

import dev.cattyn.catformat.CatFormat;
import dev.cattyn.catformat.entry.EntryContainer;
import dev.cattyn.catformat.test.colored.ColoredText;
import dev.cattyn.catformat.test.colored.ColoredTextWrapper;
import dev.cattyn.catformat.test.content.ContentWrapper;
import dev.cattyn.catformat.CatFormatImpl;
import dev.cattyn.catformat.text.Modifier;
import dev.cattyn.catformat.text.TextWrapper;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CatFormatTest {
    @Test
    public void contentTest() {
        CatFormat<String> format = createFormat(new ContentWrapper());
        String result = format.format("{red} Lorem ipsum dolor sit {blue} amet, consectetur adipiscing {}elit{}");
        assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit", result);
    }

    @Test
    public void colorTest() {
        CatFormat<ColoredText> format = createFormat(new ColoredTextWrapper());
        ColoredText result = format.format("{#f00} Lorem ipsum {blue+b} dolor {} sit");
        List<ColoredText> children = result.getChildren();
        int modifiers = 0;

        assertEquals("Lorem ipsum dolor sit", result.getString());
        assertEquals(3, children.size());
        assertEquals(new ColoredText("Lorem ipsum ", modifiers, 0xff0000), children.get(0));
        modifiers = Modifier.BOLD.getFlag();
        assertEquals(new ColoredText("dolor ", modifiers, 0x0000ff), children.get(1));
        modifiers = 0;

        assertEquals(new ColoredText("sit", modifiers, -1), children.get(2));
    }

    @Test
    public void multiModifiersTest() {
        CatFormat<ColoredText> format = new CatFormatImpl<>(new ColoredTextWrapper());
        ColoredText result = format.format("{#f00+bi} Lorem ipsum");
        List<ColoredText> children = result.getChildren();
        int modifiers = Modifier.asBits(Modifier.BOLD, Modifier.ITALIC);
        assertEquals(new ColoredText("Lorem ipsum", modifiers, 0xff0000), children.getFirst());
    }

    private <T> CatFormat<T> createFormat(TextWrapper<T> wrapper) {
        CatFormat<T> format = new CatFormatImpl<>(wrapper);
        format.add("red", 0xff0000);
        format.add("blue", 0x0000ff);
        return format;
    }
}
