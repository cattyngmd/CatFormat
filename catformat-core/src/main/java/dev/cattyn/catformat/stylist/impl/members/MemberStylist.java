package dev.cattyn.catformat.stylist.impl.members;

import dev.cattyn.catformat.entry.FormatEntry;
import dev.cattyn.catformat.stylist.ColorStylist;
import dev.cattyn.catformat.stylist.Stylist;
import dev.cattyn.catformat.stylist.annotations.Style;
import dev.cattyn.catformat.stylist.color.ColorWrapper;
import dev.cattyn.catformat.utils.ReflectionUtils;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static dev.cattyn.catformat.utils.ReflectionUtils.isInteger;

public abstract class MemberStylist<T extends Member & AnnotatedElement> implements Stylist<T[]> {
    protected final ColorStylist<?> stylist;
    protected final Object parent;

    protected MemberStylist(ColorStylist<?> stylist, Object parent) {
        this.stylist = stylist;
        this.parent = parent;
    }

    @Override
    public List<FormatEntry> getEntries(T[] members) {
        List<FormatEntry> entries = new ArrayList<>();
        for (T member : members) {
            Style style = member.getAnnotation(Style.class);

            if (style == null || isInvalid(member))
                continue;

            Class<?> type = getReturnType(member);
            ColorWrapper<?> wrapper = getWrapper(type);

            Supplier<?> supplier = getColorSupplier(member);
            if (supplier == null)
                continue;

            String name = getName(member, style);
            entries.add(buildEntry(name, supplier, wrapper));
        }

        return entries;
    }

    public abstract Supplier<?> getColorSupplier(T member);

    public abstract Class<?> getReturnType(T member);

    protected boolean isInvalid(T member) {
        if (parent != null) return false;
        return !ReflectionUtils.isStatic(member);
    }

    private String getName(T member, Style style) {
        String name = style.value();
        if (name.isEmpty()) name = member.getName();
        return name;
    }

    private ColorWrapper<?> getWrapper(Class<?> klass) {
        if (isInteger(klass)) return null;

        return stylist.getColor(klass);
    }

    private FormatEntry buildEntry(String name, Supplier<?> object, ColorWrapper<?> wrapper) {
        if (wrapper == null)
            return new FormatEntry(name, (Supplier<Integer>) object);

        return new FormatEntry(name, () -> wrapper.getRGB0(object.get()));
    }
}
