package org.exigencecorp.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

public class ToString {

    private ToString() {
    }

    /**
     * @return SimpleClassName[param1, ...]
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

}
