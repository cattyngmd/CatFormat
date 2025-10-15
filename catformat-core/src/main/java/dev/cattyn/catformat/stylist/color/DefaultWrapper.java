package dev.cattyn.catformat.stylist.color;

public class DefaultWrapper implements ColorWrapper<ColorProvider> {
    @Override
    public int getRGB(ColorProvider o) {
        return o.getRGB();
    }

    @Override
    public Class<ColorProvider> getType() {
        return ColorProvider.class;
    }
}
