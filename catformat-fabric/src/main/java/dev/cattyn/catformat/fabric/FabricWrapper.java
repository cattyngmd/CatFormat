package dev.cattyn.catformat.fabric;

import dev.cattyn.catformat.text.Modifier;
import dev.cattyn.catformat.text.TextWrapper;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class FabricWrapper implements TextWrapper<MutableText> {
    @Override
    public MutableText colored(MutableText text, int color) {
        return text.withColor(color);
    }

    @Override
    public MutableText concat(MutableText text, MutableText text2) {
        return text.append(text2);
    }

    @Override
    public MutableText modify(MutableText text, int modifiers) {
        if (Modifier.BOLD.isIn(modifiers)) text.formatted(Formatting.BOLD);
        if (Modifier.ITALIC.isIn(modifiers)) text.formatted(Formatting.ITALIC);
        if (Modifier.UNDERLINE.isIn(modifiers)) text.formatted(Formatting.UNDERLINE);
        if (Modifier.STRIKETHROUGH.isIn(modifiers)) text.formatted(Formatting.STRIKETHROUGH);
        if (Modifier.OBFUSCATED.isIn(modifiers)) text.formatted(Formatting.OBFUSCATED);
        return text;
    }

    @Override
    public MutableText newText(String content) {
        return Text.literal(content);
    }
}
