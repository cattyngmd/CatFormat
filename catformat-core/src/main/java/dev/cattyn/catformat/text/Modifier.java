package dev.cattyn.catformat.text;

import java.util.Optional;

import static dev.cattyn.catformat.utils.Constants.*;

public enum Modifier {
    BOLD,
    ITALIC,
    UNDERLINE,
    STRIKETHROUGH;

    public static Optional<Modifier> from(char c) {
        return Optional.ofNullable(switch (c) {
            case BOLD_MOD ->            BOLD;
            case ITALIC_MOD ->          ITALIC;
            case UNDERLINE_MOD ->       UNDERLINE;
            case STRIKETHROUGH_MOD ->   STRIKETHROUGH;
            default -> null;
        });
    }
}
