package org.exigencecorp.util;

import java.util.Collection;

public class Join {

    private Join() {
    }

    public static <T extends Object> String lines(T... objects) {
        return Join.join(objects, "\n");
    }

    public static <T extends Object> String lines(Collection<T> objects) {
        return Join.join(objects, "\n");
    }

    public static <T extends Object> String linesWithTickToQuote(T... objects) {
        return Tick.toQuote(Join.join(objects, "\n"));
    }

    public static <T extends Object> String comma(T... objects) {
        return Join.join(objects, ",");
    }

    public static <T extends Object> String comma(Collection<T> objects) {
        return Join.join(objects, ",");
    }

    public static <T extends Object> String commaSpace(T... objects) {
        return Join.join(objects, ", ");
    }

    public static <T extends Object> String commaSpace(Collection<T> objects) {
        return Join.join(objects, ", ");
    }

    public static <T extends Object> String space(T... objects) {
        return Join.join(objects, " ");
    }

    public static <T extends Object> String space(Collection<T> objects) {
        return Join.join(objects, " ");
    }

    public static <T extends Object> String underscore(T... objects) {
        return Join.join(objects, "_");
    }

    public static <T extends Object> String underscore(Collection<T> objects) {
        return Join.join(objects, "_");
    }

    public static <T extends Object> String join(Collection<T> objects, String seperator) {
        StringBuilder sb = new StringBuilder();
        for (T o : objects) {
            sb.append(o == null ? "" : o.toString());
            sb.append(seperator);
        }
        if (objects.size() > 0) {
            sb.delete(sb.length() - seperator.length(), sb.length());
        }
        return sb.toString();
    }

    public static <T extends Object> String join(T[] objects, String seperator) {
        StringBuilder sb = new StringBuilder();
        for (T o : objects) {
            sb.append(o == null ? "" : o.toString());
            sb.append(seperator);
        }
        if (objects.length > 0) {
            sb.delete(sb.length() - seperator.length(), sb.length());
        }
        return sb.toString();
    }

}
