package de.bluemx.stocktool.helper;

import de.bluemx.stocktool.annotations.Resolver;
import de.bluemx.stocktool.converter.Conversion;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtil {
    private ReflectionUtil() {
    }

    public static Object reflectionGet(Field field, Object source) {
        field.setAccessible(true);
        try {
            return field.get(source);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object reflectionGet(Method method, Object source) {
        try {
            return method.invoke(source);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void reflectionSet(Field field, Object source, Object value) {
        field.setAccessible(true);
        try {
            field.set(source, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Field getFieldByName(String name, Object obj) {
        try {
            return obj.getClass().getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static Method getMethodByName(String name, Object obj) {
        try {
            return obj.getClass().getMethod(name);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static Conversion getConverter(Resolver resolver) {
        try {
            return (Conversion) resolver.converter().converterClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
