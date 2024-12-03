import colored.ColoredText;
import colored.ColoredTextWrapper;
import content.ContentWrapper;
import dev.cattyn.catformat.CatFormat;
import dev.cattyn.catformat.text.Modifiers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CatFormatTest {
    @Test
    public void contentTest() {
        CatFormat<String> format = new CatFormat<>(new ContentWrapper());
        format.add("red", 0xff0000);
        format.add("blue", 0x0000ff);
        String result = format.format("${red} Lorem ipsum dolor sit ${blue} amet, consectetur adipiscing {}elit{}");
        assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit", result);
    }

    @Test
    public void colorTest() {
        CatFormat<ColoredText> format = new CatFormat<>(new ColoredTextWrapper());
        format.add("red", 0xff0000);
        format.add("blue", 0x0000ff);
        ColoredText result = format.format("${red} Lorem ipsum ${blue+bold} dolor {} sit");
        List<ColoredText> children = result.getChildren();
        Modifiers modifiers = new Modifiers();

        assertEquals("Lorem ipsum dolor sit", result.getString());
        assertEquals(3, children.size());
        assertEquals(new ColoredText("Lorem ipsum ", modifiers, 0xff0000), children.get(0));
        modifiers.setBold(true);
        assertEquals(new ColoredText("dolor ", modifiers, 0x0000ff), children.get(1));
        modifiers.setBold(false);

        assertEquals(new ColoredText("sit", modifiers, -1), children.get(2));
    }
}
