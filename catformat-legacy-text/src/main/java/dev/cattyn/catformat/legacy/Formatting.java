package dev.cattyn.catformat.legacy;

public enum Formatting {
    BLACK("black", '0'),
    DARK_BLUE("dark_blue", '1'),
    DARK_GREEN("dark_green", '2'),
    DARK_AQUA("dark_aqua", '3'),
    DARK_RED("dark_red", '4'),
    DARK_PURPLE("dark_purple", '5'),
    GOLD("gold", '6'),
    GRAY("gray", '7'),
    DARK_GRAY("dark_gray", '8'),
    BLUE("blue", '9'),
    GREEN("green", 'a'),
    AQUA("aqua", 'b'),
    RED("red", 'c'),
    LIGHT_PURPLE("light_purple", 'd'),
    YELLOW("yellow", 'e'),
    WHITE("white", 'f'),
    OBFUSCATED("obfuscated", 'k'),
    BOLD("bold", 'l'),
    ITALIC("italic", 'o'),
    UNDERLINE("underline", 'n'),
    STRIKETHROUGH("strikethrough", 'm'),
    RESET("reset", 'r');

    public static final char COLOR_SIGN = '§';

    private final String name;
    private final char code;

    Formatting(String name, char code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public char getCode() {
        return code;
    }

    public String getString() {
        return String.valueOf(COLOR_SIGN) + code;
    }

    public static Formatting fromOrdinal(int ordinal) {
        if (ordinal < 0 || ordinal >= values().length) return null;
        return values()[ordinal];
    }
}
