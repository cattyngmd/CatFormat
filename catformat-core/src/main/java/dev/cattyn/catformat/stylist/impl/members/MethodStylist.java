package dev.cattyn.catformat.stylist.impl.members;

import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.util.function.Supplier;

import static dev.cattyn.catformat.utils.ReflectionUtils.accessStatic;

public final class MethodStylist extends MemberStylist<Method> {

    @Override
    public Supplier<Integer> getColorSupplier(Method member) {
        accessStatic(member);
        try {
            MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(member.getDeclaringClass(),
                    MethodHandles.lookup());
            MethodType type = MethodType.methodType(Supplier.class);
            MethodType methodType = MethodType.methodType(Integer.class);
            MethodHandle methodHandle = lookup.unreflect(member);
            CallSite callSite = LambdaMetafactory.metafactory(lookup, "get", type,
                    MethodType.methodType(Object.class),
                    methodHandle,
                    methodType
            );
            return (Supplier<Integer>) callSite.getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected boolean isInvalid(Method member) {
        if (member.getParameterCount() != 0) return true;
        return super.isInvalid(member);
    }
}
