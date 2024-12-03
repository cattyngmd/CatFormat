package dev.cattyn.catformat;

import dev.cattyn.catformat.parsers.HexParser;
import dev.cattyn.catformat.parsers.Parser;
import dev.cattyn.catformat.parsers.NameParser;
import dev.cattyn.catformat.text.Modifiers;
import dev.cattyn.catformat.text.TextWrapper;

import java.util.HashSet;
import java.util.Set;

import static dev.cattyn.catformat.utils.Constants.*;

class Formatter<T> {
    private final Set<Character> modifiers = new HashSet<>();

    private final StringBuilder expr = new StringBuilder();
    private final StringBuilder chunk = new StringBuilder();

    private final CatFormat<T> catFormat;
    private final String target;
    private final TextWrapper<T> wrapper;
    private T core;

    private Parser parser = null;
    private int color = 0xFFFFFF;
    private boolean isExpr, skipComment, skipSpace;
    private boolean reset = true;
    private char prevOpcode;

    Formatter(CatFormat<T> catFormat, String target) {
        this.catFormat = catFormat;
        this.target = target;
        this.wrapper = catFormat.getWrapper();
        this.core = this.wrapper.newText();
    }

    public T handle() {
        for (char c : target.toCharArray()) {
            if (shouldIgnore(c))
                continue;

            boolean wasExpression = isExpr;
            isExpr = checkExpr(c, isExpr);
            if (isExpr) {
                if (c == BEGIN_EXPR) {
                    parser = getParser();
                    core = wrapper.concat(core, build(chunk));
                    modifiers.clear();
                    continue;
                }
                if (c == MODIFY_INSTR || !modifiers.isEmpty()) {
                    modifiers.add(Character.toLowerCase(c));
                    continue;
                }
                expr.append(c);
                prevOpcode = c;
                continue;
            }

            if (c == END_EXPR && wasExpression) {
                reset = parser == null;
                if (!reset) {
                    color = parser.getColor(catFormat, expr.toString());
                }
                expr.setLength(0);
                skipSpace = true;
            } else {
                chunk.append(c);
            }
            prevOpcode = c;
        }

        core = wrapper.concat(core, build(chunk));
        return core;
    }

    private boolean shouldIgnore(char opcode) {
        if (opcode == ' ' && skipSpace) {
            return true;
        }
        if (opcode == '\\' && !isExpr) {
            skipComment = true;
            return true;
        }
        if (skipComment) {
            chunk.append(opcode);
            skipComment = false;
            return true;
        }
        return false;
    }

    private Parser getParser() {
        return switch (prevOpcode) {
            case HEX_TYPE -> new HexParser();
            case NAME_TYPE -> new NameParser();
            default -> null;
        };
    }

    private T build(StringBuilder chunk) {
        T built = wrapper.newText(chunk.toString());
        if (!reset) {
            built = wrapper.colored(built, color);
        }
        Modifiers mods = getModifiers();
        built = wrapper.modify(built, mods);
        chunk.setLength(0);
        return built;
    }

    private Modifiers getModifiers() {
        Modifiers mods = new Modifiers();
        if (modifiers.isEmpty()) return mods;
        mods.setBold(modified(BOLD_MOD));
        mods.setItalic(modified(ITALIC_MOD));
        mods.setUnderline(modified(UNDERLINE_MOD));
        mods.setStrikethrough(modified(STRIKETHROUGH_MOD));
        return mods;
    }

    private boolean modified(char modifier) {
        return modifiers.contains(modifier);
    }

    private boolean checkExpr(char opcode, boolean expr) {
        if (!isExprBracket(opcode)) return expr;
        return opcode == BEGIN_EXPR;
    }
}
