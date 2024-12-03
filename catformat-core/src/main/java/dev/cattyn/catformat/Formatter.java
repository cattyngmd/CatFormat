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

    private boolean isExpr;
    private boolean skipComment;
    private boolean skipSpace;
    private boolean reset;

    private char prevOpcode;

    Formatter(CatFormat<T> catFormat, String target) {
        this.catFormat = catFormat;
        this.target = target;
        this.wrapper = catFormat.getWrapper();
        this.core = this.wrapper.newText();
        this.reset = true;
    }

    public T handle() {
        for (char c : target.toCharArray()) {
            if (shouldIgnore(c))
                continue;
            skipSpace = false;

            boolean wasExpression = isExpr;
            isExpr = checkExpr(c, isExpr);
            if (isExpr) {
                handleExpr(c);
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

        if (canConcat()) {
            core = wrapper.concat(core, build(chunk));
        }
        return core;
    }

    private void handleExpr(char opcode) {
        if (opcode == BEGIN_EXPR) {
            parser = getParser();
            if (parser != null) {
                chunk.setLength(chunk.length() - 1);
            }
            if (canConcat()) {
                core = wrapper.concat(core, build(chunk));
            }
            modifiers.clear();
            return;
        }
        if (opcode == MODIFY_INSTR || !modifiers.isEmpty()) {
            modifiers.add(Character.toLowerCase(opcode));
            return;
        }
        expr.append(opcode);
        prevOpcode = opcode;
    }

    private boolean shouldIgnore(char opcode) {
        if (opcode == ' ' && skipSpace) {
            skipSpace = false;
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

    private boolean canConcat() {
        return !chunk.isEmpty();
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
