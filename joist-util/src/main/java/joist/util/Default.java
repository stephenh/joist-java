package joist.util;

public class Default {

    public static <T> T value(T first, T defaultValue) {
        return first != null ? first : defaultValue;
    }

}
