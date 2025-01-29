package dev.cattyn.catformat.stylist.impl.members;

import dev.cattyn.catformat.formatter.FormatEntry;
import dev.cattyn.catformat.stylist.Stylist;
import dev.cattyn.catformat.stylist.annotations.Style;
import dev.cattyn.catformat.utils.ReflectionUtils;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class MemberStylist<T extends Member & AnnotatedElement> implements Stylist<T[]> {
    protected final Object parent;

    protected MemberStylist(Object parent) {
        this.parent = parent;
    }

    @Override
    public List<FormatEntry> getEntries(T[] members) {
        List<FormatEntry> entries = new ArrayList<>();
        for (T member : members) {
            Style style = member.getAnnotation(Style.class);

            if (style == null || isInvalid(member))
                continue;

            Supplier<Integer> supplier = getColorSupplier(member);
            if (supplier == null)
                continue;

            String name = getName(member, style);
            entries.add(new FormatEntry(name, supplier));
        }

        return entries;
    }

    public abstract Supplier<Integer> getColorSupplier(T member);

    protected boolean isInvalid(T member) {
        if (parent != null) return false;
        return !ReflectionUtils.isStatic(member);
    }

    private String getName(T member, Style style) {
        String name = style.value();
        if (name.isEmpty()) name = member.getName();
        return name;
    }
}
