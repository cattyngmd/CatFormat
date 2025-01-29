package dev.cattyn.catformat.stylist.impl.members;

import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.util.function.Supplier;

import static dev.cattyn.catformat.utils.ReflectionUtils.*;

public final class MethodStylist extends MemberStylist<Method> {
    public MethodStylist(Object parent) {
        super(parent);
    }

    @Override
    public Supplier<Integer> getColorSupplier(Method member) {
        access(member, parent);
        try {
            MethodHandles.Lookup lookup = lookup(member);

            MethodType type;
            if (isStatic(member)) {
                type = MethodType.methodType(Supplier.class);
            } else {
                type = MethodType.methodType(Supplier.class, parent.getClass());
            }

            MethodType methodType = MethodType.methodType(Integer.class);
            MethodHandle methodHandle = lookup.unreflect(member);
            CallSite callSite = LambdaMetafactory.metafactory(lookup, "get", type,
                    MethodType.methodType(Object.class),
                    methodHandle,
                    methodType
            );
            return buildSupplier(callSite.getTarget(), isStatic(member));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected boolean isInvalid(Method member) {
        if (member.getParameterCount() != 0) return true;
        return super.isInvalid(member);
    }

    private Supplier<Integer> buildSupplier(MethodHandle handle, boolean isStatic)
            throws Throwable {
        if (isStatic) {
            return (Supplier<Integer>) handle.invokeExact();
        }
        return (Supplier<Integer>) handle.bindTo(parent).invokeExact();
    }
}
