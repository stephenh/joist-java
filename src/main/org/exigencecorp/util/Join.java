package org.exigencecorp.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Join {

    private Join() {
    }

    public static <T extends Object> String lines(T... objects) {
        return StringUtils.join(objects, "\n");
    }

    public static <T extends Object> String lines(List<T> objects) {
        return StringUtils.join(objects, "\n");
    }

    public static <T extends Object> String linesWithTickToQuote(T... objects) {
        return Tick.toQuote(StringUtils.join(objects, "\n"));
    }

    public static <T extends Object> String comma(T... objects) {
        return StringUtils.join(objects, ",");
    }

    public static <T extends Object> String comma(List<T> objects) {
        return StringUtils.join(objects, ",");
    }

    public static <T extends Object> String commaSpace(T... objects) {
        return StringUtils.join(objects, ", ");
    }

    public static <T extends Object> String commaSpace(List<T> objects) {
        return StringUtils.join(objects, ", ");
    }

    public static <T extends Object> String space(T... objects) {
        return StringUtils.join(objects, " ");
    }

    public static <T extends Object> String space(List<T> objects) {
        return StringUtils.join(objects, " ");
    }

}
