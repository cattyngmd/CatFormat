package dev.cattyn.catformat.text;

import java.util.Optional;

import static dev.cattyn.catformat.utils.Constants.*;

public enum Modifier {
    OBFUSCATED,
    BOLD,
    STRIKETHROUGH,
    UNDERLINE,
    ITALIC;

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
}
