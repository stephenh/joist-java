package joist.util;

import joist.converter.ConverterRegistry;

public class Interpolate {

    /** @return message with each "{}" replaced with the corresponding arg */
    public static String string(String message, Object... args) {
        for (Object arg : args) {
            int i = message.indexOf("{}");
            if (i != -1) {
                message = message.substring(0, i) + String.valueOf(arg) + message.substring(i + 2);
            }
        }
        return message;
    }

    /** @return message with each "{}" replaced with the corresponding arg, converted to Strings with registry */
    public static String string(String message, ConverterRegistry registry, Object... args) {
        for (Object arg : args) {
            int i = message.indexOf("{}");
            if (i != -1) {
                message = message.substring(0, i) + registry.convert(arg, String.class) + message.substring(i + 2);
            }
        }
        return message;
    }
}
