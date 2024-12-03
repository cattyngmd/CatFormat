package colored;

import dev.cattyn.catformat.text.Modifiers;
import dev.cattyn.catformat.text.TextWrapper;

public class ColoredTextWrapper implements TextWrapper<ColoredText> {
    @Override
    public ColoredText colored(ColoredText text, int color) {
        text.setColor(color);
        return text;
    }

    @Override
    public ColoredText concat(ColoredText text, ColoredText text2) {
        text.append(text2);
        return text;
    }

    @Override
    public ColoredText modify(ColoredText text, Modifiers modifiers) {
        text.setModifiers(modifiers);
        return text;
    }

    @Override
    public ColoredText newText(String content) {
        return new ColoredText(content);
    }
}
