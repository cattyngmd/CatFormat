package dev.cattyn.catformat.stylist.wrappers;

public interface ColorWrapper<T> {
    int getRGB(T o);

    Class<T> getType();

    default int getRGB0(Object o) {
        return getRGB((T) o);
    }
}
