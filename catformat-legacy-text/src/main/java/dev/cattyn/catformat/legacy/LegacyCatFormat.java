package dev.cattyn.catformat.legacy;

import dev.cattyn.catformat.CatFormat;

public class LegacyCatFormat extends CatFormat<String> {
    public LegacyCatFormat() {
        super(new LegacyWrapper());
    }

    public LegacyCatFormat addDefaults() {
        for (Formatting value : Formatting.values()) {
            if (value == Formatting.OBFUSCATED) break;
            add(value.getName(), value.ordinal());
        }
        add(Formatting.RESET.getName(), Formatting.RESET.ordinal());
        return this;
    }
}
