package dev.cattyn.catformat.text;

import java.util.Objects;

public class Modifiers {
    private boolean bold, italic, underline, strikethrough;

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean isUnderline() {
        return underline;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public boolean isStrikethrough() {
        return strikethrough;
    }

    public void setStrikethrough(boolean strikethrough) {
        this.strikethrough = strikethrough;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Modifiers modifiers = (Modifiers) o;
        return bold == modifiers.bold && italic == modifiers.italic && underline == modifiers.underline && strikethrough == modifiers.strikethrough;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bold, italic, underline, strikethrough);
    }
}
