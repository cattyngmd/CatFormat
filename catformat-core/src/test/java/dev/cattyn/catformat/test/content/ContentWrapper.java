package dev.cattyn.catformat.test.content;

import dev.cattyn.catformat.text.Modifier;
import dev.cattyn.catformat.text.TextWrapper;

import java.util.Set;

public class ContentWrapper implements TextWrapper<String> {
    @Override
    public String colored(String text, int color) {
        return text;
    }

    @Override
    public String concat(String text, String text2) {
        return text + text2;
    }

    @Override
    public String modify(String text, Set<Modifier> modifiers) {
        return text;
    }

    @Override
    public String newText(String content) {
        return content;
    }
}
