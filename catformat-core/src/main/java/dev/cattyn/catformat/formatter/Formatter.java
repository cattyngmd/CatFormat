package dev.cattyn.catformat.formatter;

import dev.cattyn.catformat.CatFormat;
import dev.cattyn.catformat.parsers.HexParser;
import dev.cattyn.catformat.parsers.Parser;
import dev.cattyn.catformat.parsers.NameParser;
import dev.cattyn.catformat.text.Modifier;
import dev.cattyn.catformat.text.TextWrapper;
import dev.cattyn.catformat.utils.StringUtils;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import static dev.cattyn.catformat.utils.Constants.*;

public class Formatter<T> {
    private static final Map<Character, Parser> PARSER_MAP = Map.of(
            HEX_TYPE, new HexParser(),
            NAME_TYPE, new NameParser()
    );

    private final Set<Modifier> modifiers = EnumSet.noneOf(Modifier.class);

    private final StringBuilder expr = new StringBuilder();
    private final StringBuilder chunk = new StringBuilder();

    private final CatFormat<T> catFormat;
    private final String target;
    private final TextWrapper<T> wrapper;
    private ChunkType type = ChunkType.TEXT;
    private T core;

    private Parser parser = null;
    private int color = 0xFFFFFF;

    private char lastOpcode;

    public Formatter(CatFormat<T> catFormat, String target) {
        this.catFormat = catFormat;
        this.target = target;
        this.wrapper = catFormat.getWrapper();
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

        // garbage begin
        if (opcode == BEGIN_EXPR) {
            Parser parser = PARSER_MAP.get(lastOpcode);
            type = ChunkType.EXPR;
            if (parser != null) {
                StringUtils.shrink(chunk);
            }
            concat();
            modifiers.clear();
            this.parser = parser;
            return;
        }
        // garbage end

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

        expr.append(opcode);
    }

    private void handleMod(char opcode) {
        if (endExpr(opcode)) {
            return;
        }

        Modifier.from(opcode).ifPresent(modifiers::add);
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

        if (colored()) {
            color = parser.getColor(catFormat, expr.toString());
        }
        StringUtils.clear(expr);
        return true;
    }

    private T build(StringBuilder chunk) {
        T built = wrapper.newText(chunk.toString());
        if (colored()) {
            built = wrapper.colored(built, color);
        }
        built = wrapper.modify(built, EnumSet.copyOf(modifiers));
        StringUtils.clear(chunk);
        return built;
    }

    private void concat() {
        if (!chunk.isEmpty()) {
            core = wrapper.concat(core, build(chunk));
        }
    }

    private boolean colored() {
        return parser != null;
    }
}
