package org.exigencecorp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

public class ToString {

    private static Pattern standaloneTick = Pattern.compile("(?<!')'(?!')");
    private static Pattern twoTicks = Pattern.compile("''");

    private ToString() {
    }

    /**
     * @param parameters Strings that are (safely) evaluated as OGNL then interpolated
     * @return SimpleClassName[evaledParam1, ...]
     */
    public static String to(Object object, Object... parameters) {
        if (object == null) {
            return "null";
        }
        List<String> transformed = new ArrayList<String>();
        for (Object parameter : parameters) {
            transformed.add(ObjectUtils.toString(parameter));
        }
        return object.getClass().getSimpleName() + "[" + StringUtils.join(transformed, ",") + "]";
    }

    /** @return message with each "{}" replaced with the corresponding arg */
    public static String interpolate(String message, Object... args) {
        for (Object arg : args) {
            message = StringUtils.replaceOnce(message, "{}", String.valueOf(arg));
        }
        return message;
    }

    /** @return message with all ' -> " except for '' */
    public static String tickToQuote(String message) {
        message = ToString.standaloneTick.matcher(message).replaceAll("\"");
        message = ToString.twoTicks.matcher(message).replaceAll("'");
        return message;
    }

}
