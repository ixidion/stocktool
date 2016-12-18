package de.bluemx.stocktool.helper;

import com.google.inject.Singleton;
import de.bluemx.stocktool.annotations.Resolver;
import de.bluemx.stocktool.converter.Conversion;

import java.lang.reflect.Field;

@Singleton
public class ReflectionUtil {

    public Object reflectionGet(Field field, Object source) {
        field.setAccessible(true);
        try {
            return field.get(source);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void reflectionSet(Field field, Object source, Object value) {
        field.setAccessible(true);
        try {
            field.set(source, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Field getFieldByName(String name, Object obj) {
        try {
            return obj.getClass().getField(name);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public Conversion getConverter(Resolver resolver) {
        try {
            return (Conversion) resolver.converterClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
