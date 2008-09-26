package org.exigencecorp.domainobjects;

import java.util.Arrays;
import java.util.Comparator;

import org.apache.commons.lang.ArrayUtils;

public class Codes {

    private Codes() {
    }

    public static <T extends Code> T[] valuesByCode(T[] codes) {
        T[] copy = (T[]) ArrayUtils.clone(codes);
        Arrays.sort(copy, new Comparator<T>() {
            public int compare(T c1, T c2) {
                return c1.getCode().compareTo(c2.getCode());
            }
        });
        return copy;
    }

    public static <T extends Code> T[] valuesByName(T[] codes) {
        T[] copy = (T[]) ArrayUtils.clone(codes);
        Arrays.sort(copy, new Comparator<T>() {
            public int compare(T c1, T c2) {
                return c1.getName().compareTo(c2.getName());
            }
        });
        return copy;
    }

    public static <T extends Code> T fromInt(T[] codes, int i) {
        for (T code : codes) {
            if (code.toInt() == i) {
                return code;
            }
        }
        return null;
    }

    public static <T extends Code> T fromCode(T[] codes, String code) {
        for (T c : codes) {
            if (c.getCode().equals(code)) {
                return c;
            }
        }
        return null;
    }

}
