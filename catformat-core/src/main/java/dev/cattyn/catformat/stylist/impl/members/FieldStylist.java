package dev.cattyn.catformat.stylist.impl.members;

import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.util.function.Supplier;

import static dev.cattyn.catformat.utils.ReflectionUtils.*;

public final class FieldStylist extends MemberStylist<Field> {
    @Override
    public Supplier<Integer> getColorSupplier(Field member) {
        accessStatic(member);
        if (isFinal(member)) {
            return immutable(member);
        }
        return mutable(member);
    }


    private Supplier<Integer> immutable(Field field) {
        try {
            Object o = field.get(null);
            return () -> (Integer) o;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Supplier<Integer> mutable(Field field) {
        VarHandle handle = unreflect(field);
        return () -> (Integer) handle.get();
    }
}
