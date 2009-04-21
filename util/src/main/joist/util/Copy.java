package joist.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Copy {

    private Copy() {
    }

    public static <T> List<T> shallow(Collection<T> source) {
        return new ArrayList<T>(source);
    }

    public static <T> List<T> reverse(List<T> source) {
        List<T> r = new ArrayList<T>(source);
        Collections.reverse(r);
        return r;
    }

    public static <T> List<T> list(T... array) {
        List<T> list = new ArrayList<T>();
        if (array != null) {
            for (T a : array) {
                list.add(a);
            }
        }
        return list;
    }

    public static <T> List<T> list(Collection<T> collection) {
        return new ArrayList<T>(collection);
    }

    public static <T> List<T> unique(Collection<T> source) {
        List<T> target = new ArrayList<T>();
        for (T s : source) {
            if (!target.contains(s)) {
                target.add(s);
            }
        }
        return target;
    }

    public static <T> List<T> union(Collection<? extends T>... sources) {
        ArrayList<T> union = new ArrayList<T>();
        for (Collection<? extends T> source : sources) {
            union.addAll(source);
        }
        return union;
    }

    public static <T> T[] array(Class<T> type, List<T> list) {
        T[] array = (T[]) Array.newInstance(type, list.size());
        return list.toArray(array);
    }

    public static <T> T[] array(Class<T> type, Set<T> set) {
        T[] array = (T[]) Array.newInstance(type, set.size());
        return set.toArray(array);
    }

}
