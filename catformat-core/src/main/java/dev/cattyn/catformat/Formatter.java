package dev.cattyn.catformat;

import dev.cattyn.catformat.parser.HexParser;
import dev.cattyn.catformat.parser.Parser;
import dev.cattyn.catformat.parser.NameParser;
import dev.cattyn.catformat.text.Modifier;
import dev.cattyn.catformat.text.TextStyle;
import dev.cattyn.catformat.text.TextWrapper;
import dev.cattyn.catformat.utils.ChunkType;
import dev.cattyn.catformat.utils.StringUtils;

import java.util.ArrayDeque;

import static dev.cattyn.catformat.utils.Constants.*;

public class Formatter<T> {
    private final StringBuilder expr = new StringBuilder();
    private final StringBuilder chunk = new StringBuilder();

    private final ArrayDeque<TextStyle> styles = new ArrayDeque<>();

    private final CatFormatImpl<T> catFormat;
    private final String target;
    private final TextWrapper<T> wrapper;
    private ChunkType type = ChunkType.TEXT;
    private T core;

    private Parser parser = null;
    private int modifiers;

    private char lastOpcode;
    private boolean lastBlank = true;

    public Formatter(CatFormatImpl<T> catFormat, String target) {
        this.catFormat = catFormat;
        this.target = target;
        this.wrapper = catFormat.wrapper();
        this.core = this.wrapper.newText();
    }

    public T handle() {
        int len = target.length();
        for (int i = 0; i < len; i++) {
            char c = target.charAt(i);
            switch (type) {
                case TEXT          -> handleText(c);
                case EXPR          -> handleExpr(c);
                case MOD           -> handleMod(c);
                case ESCAPE        -> handleEscape(c, false);
                case ESCAPE_STRICT -> handleEscape(c, true);
            }
            lastOpcode = c;
        }

        concat();
        return core;
    }

    private void handleText(char opcode) {
        if (opcode == ESCAPE) {
            type = ChunkType.ESCAPE;
            return;
        }

        if (opcode == BEGIN_EXPR) {
            beginExpr();
            return;
        }

        lastBlank = opcode == BLANK;

        chunk.append(opcode);
    }

    private void handleExpr(char opcode) {
        if (opcode == MODIFY_INSTR) {
            type = ChunkType.MOD;
            return;
        }

        if (endExpr(opcode)) {
            return;
        }

        if (lastOpcode == BEGIN_EXPR
                && opcode == HEX_TYPE) {
            parser = new HexParser();
            return;
        }

        if (parser == null) {
            parser = new NameParser();
        }

        expr.append(opcode);
    }

    private void handleMod(char opcode) {
        if (endExpr(opcode)) {
            return;
        }

        Modifier.from(opcode).ifPresent(mod -> modifiers = mod.with(modifiers));
    }

    private void handleEscape(char opcode, boolean onlyBlank) {
        if (onlyBlank && opcode == BEGIN_EXPR) {
            beginExpr();
            return;
        }

        if (isExprBracket(opcode)) {
            type = ChunkType.TEXT;
            StringUtils.shrink(chunk);
            chunk.append(opcode);
            return;
        }

        type = ChunkType.TEXT;

        if (lastBlank && opcode == BLANK && lastOpcode == END_EXPR) {
            return;
        }

        if (!isExprBracket(lastOpcode)) {
            chunk.append(lastOpcode);
        }
        chunk.append(opcode);
    }

    private boolean endExpr(char c) {
        if (c != END_EXPR) return false;
        type = ChunkType.ESCAPE_STRICT;

        if (parser != null) {
            int color = parser.getColor(catFormat.entries(), expr.toString());
            styles.push(new TextStyle(color, modifiers));
        } else {
            styles.pop();
        }
        StringUtils.clear(expr);
        return true;
    }

    private void beginExpr() {
        type = ChunkType.EXPR;
        concat();
        modifiers = 0;
        parser = null;
    }

    private T build(StringBuilder chunk) {
        T built = wrapper.newText(chunk.toString());
        if (!styles.isEmpty()) {
            TextStyle style = styles.peek();
            built = wrapper.colored(built, style.color());
            built = wrapper.modify(built, style.modifiers());
        }
        StringUtils.clear(chunk);
        return built;
    }

    private void concat() {
        if (!chunk.isEmpty()) {
            core = wrapper.concat(core, build(chunk));
        }
    }
}
