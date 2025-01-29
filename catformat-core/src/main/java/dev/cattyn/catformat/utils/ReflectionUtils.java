package dev.cattyn.catformat.utils;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

public final class ReflectionUtils {
    private ReflectionUtils() {
        throw new AssertionError();
    }

    public static boolean isStatic(Member field) {
        return Modifier.isStatic(field.getModifiers());
    }

    public static boolean isFinal(Member field) {
        return Modifier.isFinal(field.getModifiers());
    }

    public static void accessStatic(AccessibleObject object) {
        if (!object.canAccess(null)) {
            object.setAccessible(true);
        }
    }

    public static VarHandle unreflect(Field field) {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            return lookup.unreflectVarHandle(field);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
