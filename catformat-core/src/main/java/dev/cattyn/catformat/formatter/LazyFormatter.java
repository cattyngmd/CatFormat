package dev.cattyn.catformat.formatter;

import java.util.Formatter;

public final class LazyFormatter implements TextFormatter {
    public static final LazyFormatter INSTANCE = new LazyFormatter();

    private static final ThreadLocal<FormatPair> COMMON_FORMATTER = ThreadLocal.withInitial(FormatPair::new);

    private LazyFormatter() { }

    @Override
    public String format(String s, Object... objects) {
        FormatPair pair = COMMON_FORMATTER.get();
        pair.builder.setLength(0);
        return pair.formatter.format(s, objects).toString();
    }

    private static class FormatPair {
        private final StringBuilder builder = new StringBuilder();
        private final Formatter formatter = new Formatter(builder);
    }
}
