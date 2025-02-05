package dev.cattyn.catformat.stylist.impl.members;

import dev.cattyn.catformat.stylist.ColorStylist;

import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.util.function.Supplier;

import static dev.cattyn.catformat.utils.ReflectionUtils.*;

public final class FieldStylist extends MemberStylist<Field> {
    public FieldStylist(ColorStylist<?> stylist, Object parent) {
        super(stylist, parent);
    }

    @Override
    public Supplier<?> getColorSupplier(Field member) {
        access(member, parent);
        if (isFinal(member)) {
            return immutable(member);
        }
        return mutable(member);
    }

    @Override
    public Class<?> getReturnType(Field member) {
        return member.getType();
    }

    private Supplier<?> immutable(Field field) {
        try {
            Object o = field.get(parent);
            return () -> o;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Supplier<?> mutable(Field field) {
        VarHandle handle = unreflect(field);
        if (parent != null)
            return () -> handle.get(parent);
        return handle::get;
    }
}
