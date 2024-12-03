package colored;

import dev.cattyn.catformat.text.Modifiers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// simple text object
public class ColoredText {
    private final List<ColoredText> children = new ArrayList<>();
    private final String content;

    private Modifiers modifiers = new Modifiers();
    private int color = -1;

    public ColoredText(String content) {
        this.content = content;
    }

    public ColoredText(String content, Modifiers modifiers, int color) {
        this.content = content;
        this.modifiers = modifiers;
        this.color = color;
    }

    public void append(ColoredText text) {
        children.add(text);
    }

    public String getString() {
        if (children.isEmpty()) return content;
        return content + children.stream()
                .map(ColoredText::getString)
                .reduce("", String::concat);
    }

    public List<ColoredText> getChildren() {
        return children;
    }

    public void setModifiers(Modifiers modifiers) {
        this.modifiers = modifiers;
    }


    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColoredText that = (ColoredText) o;
        return color == that.color && Objects.equals(content, that.content) && Objects.equals(modifiers, that.modifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, modifiers, color);
    }
}
