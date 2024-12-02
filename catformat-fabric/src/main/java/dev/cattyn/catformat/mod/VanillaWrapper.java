package dev.cattyn.catformat.mod;

import dev.cattyn.catformat.text.Modifiers;
import dev.cattyn.catformat.text.TextWrapper;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class VanillaWrapper implements TextWrapper<MutableText> {
    @Override
    public MutableText colored(MutableText text, int color) {
        return text.withColor(color);
    }

    @Override
    public MutableText concat(MutableText text, MutableText text2) {
        return text.append(text2);
    }

    @Override
    public MutableText modify(MutableText text, Modifiers modifiers) {
        if (modifiers.isBold()) text.formatted(Formatting.BOLD);
        if (modifiers.isItalic()) text.formatted(Formatting.ITALIC);
        if (modifiers.isUnderline()) text.formatted(Formatting.UNDERLINE);
        if (modifiers.isStrikethrough()) text.formatted(Formatting.STRIKETHROUGH);
        return text;
    }

    @Override
    public MutableText newText(String content) {
        return Text.literal(content);
    }
}
