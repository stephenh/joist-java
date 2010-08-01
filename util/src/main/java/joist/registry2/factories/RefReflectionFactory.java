package joist.registry2.factories;

import joist.util.Reflection;

public class RefReflectionFactory<T> implements RefFactory<T> {

    private final Class<? extends T> type;

    public RefReflectionFactory(Class<? extends T> type) {
        this.type = type;
    }

    public T create() {
        return Reflection.newInstance(this.type);
    }

    public void destroy(T resource) {
    }

}
