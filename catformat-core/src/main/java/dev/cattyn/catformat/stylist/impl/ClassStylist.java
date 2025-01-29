package dev.cattyn.catformat.stylist.impl;

import dev.cattyn.catformat.formatter.FormatEntry;
import dev.cattyn.catformat.stylist.Stylist;
import dev.cattyn.catformat.stylist.impl.members.FieldStylist;
import dev.cattyn.catformat.stylist.impl.members.MethodStylist;

import java.util.ArrayList;
import java.util.List;

public class ClassStylist implements Stylist<Class<?>> {
    private final FieldStylist fields = new FieldStylist();
    private final MethodStylist methods = new MethodStylist();

    @Override
    public List<FormatEntry> getEntries(Class<?> target) {
        List<FormatEntry> entries = new ArrayList<>();
        entries.addAll(fields.getEntries(target.getDeclaredFields()));
        entries.addAll(methods.getEntries(target.getDeclaredMethods()));
        return entries;
    }

}
