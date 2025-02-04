package dev.cattyn.catformat.test.colored;

import dev.cattyn.catformat.text.Modifier;

import java.util.*;

// simple text object
public class ColoredText {
    private final List<ColoredText> children = new ArrayList<>();
    private final String content;

    private int modifiers;
    private int color = -1;

    public ColoredText(String content) {
        this.content = content;
        this.modifiers = 0;
    }

    public ColoredText(String content, int modifiers, int color) {
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

    public void setModifiers(int modifiers) {
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
