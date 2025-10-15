package dev.cattyn.catformat.stylist.color;

public class ARGBWrapper implements ColorWrapper<ARGBProvider> {
    @Override
    public int getRGB(ARGBProvider o) {
        return o.getARGB();
    }

    @Override
    public Class<ARGBProvider> getType() {
        return ARGBProvider.class;
    }
}
