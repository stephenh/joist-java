package org.exigencecorp.util;

import org.apache.commons.lang.StringUtils;

public class Interpolate {

    /** @return message with each "{}" replaced with the corresponding arg */
    public static String string(String message, Object... args) {
        for (Object arg : args) {
            message = StringUtils.replaceOnce(message, "{}", String.valueOf(arg));
        }
        return message;
    }

}
