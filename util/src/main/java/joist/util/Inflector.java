package joist.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Inflector {

    private static final Pattern lower = Pattern.compile("[a-z]");
    private static final Pattern upper = Pattern.compile("[A-Z0-9]");

    private Inflector() {
    }

    public static String underscore(String camelCased) {
        List<String> parts = Inflector.split(camelCased);
        for (int i = 0; i < parts.size(); i++) {
            parts.set(i, parts.get(i).toLowerCase());
        }
        return Join.underscore(parts);
    }

    public static String camelize(String s) {
        StringBuffer name = new StringBuffer();
        for (String part : s.split(" |_")) {
            name.append(Inflector.capitalize(part));
        }
        return name.toString();
    }

    public static String humanize(String camelCased) {
        List<String> parts = Inflector.split(camelCased);
        for (int i = 0; i < parts.size(); i++) {
            parts.set(i, Inflector.capitalize(parts.get(i)));
        }
        return Join.space(parts);
    }

    public static String capitalize(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return Character.toTitleCase(str.charAt(0)) + str.substring(1);
    }

    public static String uncapitalize(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    /** Splits word into groups based on where underscores and case changes are. */
    private static List<String> split(String word) {
        List<String> parts = new ArrayList<String>();
        boolean wasLower = false;
        int at = 0;
        int length = word.length();
        for (int i = 0; i < length; i++) {
            String c = word.substring(i, i + 1);
            if ("_".equals(c)) {
                parts.add(word.substring(at, i));
                at = i + 1;
            } else if (wasLower && Inflector.upper.matcher(c).matches()) {
                parts.add(word.substring(at, i));
                at = i;
                wasLower = false;
            } else if (!wasLower && Inflector.lower.matcher(c).matches()) {
                wasLower = true;
            }
        }
        if (at != length) {
            parts.add(word.substring(at));
        }
        return parts;
    }

}
