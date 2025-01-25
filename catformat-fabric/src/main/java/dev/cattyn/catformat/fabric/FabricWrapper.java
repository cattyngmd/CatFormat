package dev.cattyn.catformat.fabric;

import dev.cattyn.catformat.text.Modifier;
import dev.cattyn.catformat.text.TextWrapper;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Set;

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
    public MutableText modify(MutableText text, Set<Modifier> modifiers) {
        if (modifiers.contains(Modifier.BOLD)) text.formatted(Formatting.BOLD);
        if (modifiers.contains(Modifier.ITALIC)) text.formatted(Formatting.ITALIC);
        if (modifiers.contains(Modifier.UNDERLINE)) text.formatted(Formatting.UNDERLINE);
        if (modifiers.contains(Modifier.STRIKETHROUGH)) text.formatted(Formatting.STRIKETHROUGH);
        return text;
    }

    @Override
    public MutableText newText(String content) {
        return Text.literal(content);
    }
}
