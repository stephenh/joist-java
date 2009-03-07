package org.exigencecorp.util;

import java.util.ArrayList;
import java.util.List;

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
            transformed.add(parameter == null ? "" : parameter.toString());
        }
        return object.getClass().getSimpleName() + "[" + Join.comma(transformed, ",") + "]";
    }

}
