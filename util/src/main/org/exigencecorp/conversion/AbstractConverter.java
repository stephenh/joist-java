package org.exigencecorp.conversion;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractConverter<T, U> implements Converter<T, U> {

    private final Class<T> one;
    private final Class<U> two;
    protected ConverterRegistry registry;

    protected AbstractConverter() {
        for (Class<?> type = this.getClass(); type != null; type = type.getSuperclass()) {
            if (type.getSuperclass().equals(AbstractConverter.class) || type.getSuperclass().equals(AbstractOneWayConverter.class)) {
                ParameterizedType ptype = (ParameterizedType) type.getGenericSuperclass();
                this.one = (Class<T>) ptype.getActualTypeArguments()[0];
                this.two = (Class<U>) ptype.getActualTypeArguments()[1];
                return;
            }
        }
        // Should never happen
        throw new RuntimeException("Could not infer the types from the AbstractConverter generic superclass");
    }

    public Class<T> getTypeOne() {
        return this.one;
    }

    public Class<U> getTypeTwo() {
        return this.two;
    }

    public void setConverterRegistry(ConverterRegistry registry) {
        this.registry = registry;
    }

}
