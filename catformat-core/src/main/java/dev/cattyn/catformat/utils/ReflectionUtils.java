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

    public static boolean isInteger(Class<?> klass) {
        return int.class == klass || Integer.class == klass;
    }

    public static void access(AccessibleObject object, Object obj) {
        if (object.canAccess(obj)) {
            return;
        }

        object.setAccessible(true);
    }

    public static VarHandle unreflect(Field field) {
        try {
            MethodHandles.Lookup lookup = lookup(field);
            return lookup.unreflectVarHandle(field);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static MethodHandles.Lookup lookup(Member member) throws IllegalAccessException {
        return MethodHandles.privateLookupIn(member.getDeclaringClass(), MethodHandles.lookup());
    }
}
