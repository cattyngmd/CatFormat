package dev.cattyn.catformat.stylist.color;

import java.awt.*;

public class AwtWrapper implements ColorWrapper<Color> {
    @Override
    public int getRGB(Color o) {
        return o.hashCode();
    }

    @Override
    public Class<Color> getType() {
        return Color.class;
    }
}
