package dev.cattyn.catformat.fabric;

import dev.cattyn.catformat.CatFormat;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

public class FabricCatFormat extends CatFormat<MutableText> {
    public FabricCatFormat() {
        super(new FabricWrapper());
    }

    public FabricCatFormat addVanilla() {
        for (Formatting value : Formatting.values()) {
            if (!value.isColor() || value.getColorValue() == null) {
                continue;
            }
            add(value.getName(), value.getColorValue());
        }
        return this;
    }
}
