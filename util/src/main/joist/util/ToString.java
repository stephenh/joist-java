package joist.util;

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
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i] == null) {
                parameters[i] = "null";
            }
        }
        return object.getClass().getSimpleName() + "[" + Join.comma(parameters) + "]";
    }

}
