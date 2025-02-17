package dev.cattyn.catformat;

import dev.cattyn.catformat.parser.HexParser;
import dev.cattyn.catformat.parser.Parser;
import dev.cattyn.catformat.parser.NameParser;
import dev.cattyn.catformat.text.Modifier;
import dev.cattyn.catformat.text.TextStyle;
import dev.cattyn.catformat.text.TextWrapper;
import dev.cattyn.catformat.utils.ChunkType;
import dev.cattyn.catformat.utils.StringUtils;

import java.util.Stack;

import static dev.cattyn.catformat.utils.Constants.*;

public class Formatter<T> {
    private final StringBuilder expr = new StringBuilder();
    private final StringBuilder chunk = new StringBuilder();

    private final Stack<TextStyle> styles = new Stack<>();

    private final CatFormatImpl<T> catFormat;
    private final String target;
    private final TextWrapper<T> wrapper;
    private ChunkType type = ChunkType.TEXT;
    private T core;

    private Parser parser = null;
    private int modifiers;

    private char lastOpcode;

    public Formatter(CatFormatImpl<T> catFormat, String target) {
        this.catFormat = catFormat;
        this.target = target;
        this.wrapper = catFormat.wrapper();
        this.core = this.wrapper.newText();
    }

    public T handle() {
        for (char c : target.toCharArray()) {
            switch (type) {
                case TEXT   -> handleText(c);
                case EXPR   -> handleExpr(c);
                case MOD    -> handleMod(c);
                case ESCAPE -> handleEscape(c);
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
            type = ChunkType.EXPR;
            concat();
            modifiers = 0;
            parser = null;
            return;
        }

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

    private void handleEscape(char opcode) {
        if (isExprBracket(opcode)) {
            type = ChunkType.TEXT;
            StringUtils.shrink(chunk);
            chunk.append(opcode);
            return;
        }

        type = ChunkType.TEXT;

        if (opcode == BLANK && lastOpcode == END_EXPR) {
            return;
        }

        if (!isExprBracket(lastOpcode)) {
            chunk.append(lastOpcode);
        }
        chunk.append(opcode);
    }

    private boolean endExpr(char c) {
        if (c != END_EXPR) return false;
        type = ChunkType.ESCAPE;

        if (parser != null) {
            int color = parser.getColor(catFormat.entries(), expr.toString());
            styles.push(new TextStyle(color, modifiers));
        } else {
            styles.pop();
        }
        StringUtils.clear(expr);
        return true;
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
