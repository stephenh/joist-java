package joist.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflection {

    private Reflection() {
    }

    public static Object invoke(Method method, Object target, Object... params) {
        try {
            return method.invoke(target, params);
        } catch (InvocationTargetException ite) {
            if (ite.getTargetException() instanceof RuntimeException) {
                throw (RuntimeException) ite.getTargetException();
            } else {
                throw new RuntimeException(ite.getTargetException());
            }
        } catch (IllegalAccessException iea) {
            throw new RuntimeException(iea);
        }
    }

    public static Object get(Field field, Object target) {
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return field.get(target);
        } catch (IllegalAccessException iae) {
            throw new RuntimeException(iae);
        }
    }

    public static void set(Field field, Object target, Object value) {
        try {
            field.set(target, value);
        } catch (IllegalAccessException iae) {
            throw new RuntimeException(iae);
        }
    }

    public static Object newInstance(String className) {
        return Reflection.newInstance(Reflection.forName(className));
    }

    public static <T> T newInstance(Class<T> type) {
        try {
            for (Constructor<?> c : type.getConstructors()) {
                if (c.getParameterTypes().length == 0) {
                    // found the nullary constructor, so instantiate an object
                    return type.newInstance();
                }
            }
            // nullary constructor does not exist for given type
            throw new RuntimeException("Given type (" + type.getCanonicalName() + ") must have a default constructor.");
        } catch (IllegalAccessException iea) {
            throw new RuntimeException(iea);
        } catch (InstantiationException ie) {
            throw new RuntimeException(ie);
        }
    }

    public static <T> T newInstanceOrNull(Class<T> type) {
        if (type == null) {
            return null;
        }

        try {
            for (Constructor<?> c : type.getConstructors()) {
                if (c.getParameterTypes().length == 0) {
                    // found the nullary constructor, so instantiate an object
                    return type.newInstance();
                }
            }
            // nullary constructor does not exist for given type
            Log.warn("Given type (" + type.getCanonicalName() + ") must have a default constructor. Returning null.");
        } catch (IllegalAccessException iea) {
            Log.warn(iea.getMessage(), iea);
        } catch (InstantiationException ie) {
            Log.warn(ie.getMessage(), ie);
        }
        return null;
    }

    public static Class<?> forName(String className) {
        Class<?> c = Reflection.forNameOrNull(className);
        if (c == null) {
            throw new RuntimeException("Class " + className + " was not found.");
        }
        return c;
    }

    public static Class<?> forNameOrNull(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

}
