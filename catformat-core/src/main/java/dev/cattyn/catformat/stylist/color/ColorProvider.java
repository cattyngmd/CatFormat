package dev.cattyn.catformat.stylist.color;

public interface ColorProvider {
    int getRGB();

    interface Mutable extends ColorProvider { }
}
