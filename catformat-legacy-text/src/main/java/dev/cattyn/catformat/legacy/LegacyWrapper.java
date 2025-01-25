package dev.cattyn.catformat.legacy;

import dev.cattyn.catformat.text.Modifier;
import dev.cattyn.catformat.text.TextWrapper;

import java.util.Set;

public class LegacyWrapper implements TextWrapper<String> {
    @Override
    public String colored(String text, int color) {
        Formatting formatting = Formatting.fromOrdinal(color);
        if (formatting == null) return text;
        return formatting.getString() + text;
    }

    @Override
    public String concat(String text, String text2) {
        return text + text2;
    }

    @Override
    public String modify(String text, Set<Modifier> modifiers) {
        StringBuilder sb = new StringBuilder();
        for (Modifier modifier : modifiers) {
            int i = Formatting.OBFUSCATED.ordinal() + modifier.ordinal();
            Formatting formatting = Formatting.fromOrdinal(i);
            if (formatting == null) continue; // ???
            sb.append(formatting.getString());
        }
        sb.append(text);

        return sb.toString();
    }

    @Override
    public String newText(String content) {
        return content;
    }
}
