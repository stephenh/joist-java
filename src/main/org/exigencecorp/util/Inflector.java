package org.exigencecorp.util;

import org.apache.commons.lang.StringUtils;

public class Inflector {

    private static String lowerToUpper = "(?<=[a-z])(?=[A-Z]|[0-9])";
    private static String underscore = "_";
    private static String camelCaseSplit = "(" + Inflector.lowerToUpper + ")|(" + Inflector.underscore + ")";

    private Inflector() {
    }

    public static String underscore(String camelCased) {
        String name = "";
        String[] parts = camelCased.split(Inflector.camelCaseSplit);
        for (int i = 0; i < parts.length; i++) {
            name += parts[i].toLowerCase();
            if (i != parts.length - 1) {
                name += "_";
            }
        }
        return name;
    }

    public static String camelize(String s) {
        StringBuffer name = new StringBuffer();
        for (String part : s.split(" |_")) {
            name.append(StringUtils.capitalize(part));
        }
        return name.toString();
    }

    public static String humanize(String camelCased) {
        String name = "";
        String[] parts = camelCased.split(Inflector.camelCaseSplit);
        for (int i = 0; i < parts.length; i++) {
            name += StringUtils.capitalize(parts[i]);
            if (i != parts.length - 1) {
                name += " ";
            }
        }
        return name;
    }

}
