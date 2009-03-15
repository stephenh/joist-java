package org.exigencecorp.util;

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
        return object.getClass().getSimpleName() + "[" + Join.comma(parameters) + "]";
    }

}
