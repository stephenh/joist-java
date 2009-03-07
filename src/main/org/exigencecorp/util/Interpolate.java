package org.exigencecorp.util;

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
}
