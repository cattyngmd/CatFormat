package dev.cattyn.catformat.text;

public interface TextWrapper<T> {
    T colored(T text, int color);

    T concat(T text, T text2);

    T modify(T text, Modifiers modifiers);

    T newText(String content);

    default T newText() {
        return newText("");
    }
}
