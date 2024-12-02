package dev.cattyn.catformat.utils;

public final class Constants {
    public static final char BEGIN_EXPR = '{';
    public static final char END_EXPR = '}';

    public static final char HEX_TYPE = '#';
    public static final char NAME_TYPE = '$';

    public static final char MODIFY_INSTR = '+';

    public static final char BOLD_MOD = 'b';
    public static final char ITALIC_MOD = 'i';
    public static final char UNDERLINE_MOD = 'u';
    public static final char STRIKETHROUGH_MOD = 's';

    public static boolean isExprBracket(char c) {
        return c == BEGIN_EXPR || c == END_EXPR;
    }
}
