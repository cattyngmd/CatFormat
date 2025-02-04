package dev.cattyn.catformat.text;

import java.util.Optional;

import static dev.cattyn.catformat.utils.Constants.*;

public enum Modifier {
    OBFUSCATED,
    BOLD,
    STRIKETHROUGH,
    UNDERLINE,
    ITALIC;

    private final int flag = 1 << ordinal();

    public static Optional<Modifier> from(char c) {
        return Optional.ofNullable(switch (c) {
            case OBFUSCATED_MOD ->      OBFUSCATED;
            case BOLD_MOD ->            BOLD;
            case STRIKETHROUGH_MOD ->   STRIKETHROUGH;
            case UNDERLINE_MOD ->       UNDERLINE;
            case ITALIC_MOD ->          ITALIC;
            default -> null;
        });
    }

    public static int asBits(Modifier... modifiers) {
        int mod = 0;
        for (Modifier m : modifiers) {
            mod = m.with(mod);
        }
        return mod;
    }

    public int getFlag() {
        return flag;
    }

    public boolean isIn(int flags) {
        return (flags & getFlag()) > 0;
    }

    public int with(int flags) {
        return flags | getFlag();
    }
}
