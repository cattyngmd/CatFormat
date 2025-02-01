package dev.cattyn.catformat.test;

import dev.cattyn.catformat.CatFormat;
import dev.cattyn.catformat.entry.EntryContainer;
import dev.cattyn.catformat.test.colored.ColoredText;
import dev.cattyn.catformat.test.colored.ColoredTextWrapper;
import dev.cattyn.catformat.test.content.ContentWrapper;
import dev.cattyn.catformat.CatFormatImpl;
import dev.cattyn.catformat.text.Modifier;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CatFormatTest {
    @Test
    public void contentTest() {
        CatFormat<String> format = new CatFormatImpl<>(new ContentWrapper());
        format.add("red", 0xff0000);
        format.add("blue", 0x0000ff);
        String result = format.format("${red} Lorem ipsum dolor sit ${blue} amet, consectetur adipiscing {}elit{}");
        assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit", result);
    }

    @Test
    public void colorTest() {
        CatFormat<ColoredText> format = new CatFormatImpl<>(new ColoredTextWrapper());
        format.add("red", 0xff0000);
        format.add("blue", 0x0000ff);
        ColoredText result = format.format("${red} Lorem ipsum ${blue+b} dolor {} sit");
        List<ColoredText> children = result.getChildren();
        EnumSet<Modifier> modifiers = EnumSet.noneOf(Modifier.class);

        assertEquals("Lorem ipsum dolor sit", result.getString());
        assertEquals(3, children.size());
        assertEquals(new ColoredText("Lorem ipsum ", modifiers, 0xff0000), children.get(0));
        modifiers.add(Modifier.BOLD);
        assertEquals(new ColoredText("dolor ", modifiers, 0x0000ff), children.get(1));
        modifiers.remove(Modifier.BOLD);

        assertEquals(new ColoredText("sit", modifiers, -1), children.get(2));
    }
}
