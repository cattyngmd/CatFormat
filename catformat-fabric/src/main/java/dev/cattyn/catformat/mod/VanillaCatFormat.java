package dev.cattyn.catformat.mod;

import dev.cattyn.catformat.CatFormat;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

public class VanillaCatFormat extends CatFormat<MutableText> {
    public VanillaCatFormat() {
        super(new VanillaWrapper());
    }

    public CatFormat<MutableText> addVanilla() {
        for (Formatting value : Formatting.values()) {
            if (!value.isColor() || value.getColorValue() == null) {
                continue;
            }
            add(value.getName(), value.getColorValue());
        }
        return this;
    }
}
