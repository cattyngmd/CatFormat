package dev.cattyn.catformat.formatter;

public final class SimpleFormatter implements TextFormatter {
    @Override
    public String format(String text, Object... o) {
        return String.format(text, o);
    }
}
