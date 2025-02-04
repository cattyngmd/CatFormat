package dev.cattyn.catformat.legacy;

import dev.cattyn.catformat.CatFormatImpl;

public class LegacyCatFormat extends CatFormatImpl<String> {
    public LegacyCatFormat() {
        this(true);
    }

    public LegacyCatFormat(boolean vanilla) {
        super(new LegacyWrapper());
        if (vanilla) {
            addDefaults();
        }
    }

    private LegacyCatFormat addDefaults() {
        for (Formatting value : Formatting.values()) {
            if (value == Formatting.OBFUSCATED) break;
            add(value.getName(), value.ordinal());
        }
        add(Formatting.RESET.getName(), Formatting.RESET.ordinal());
        return this;
    }
}
