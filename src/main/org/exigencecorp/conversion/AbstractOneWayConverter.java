package org.exigencecorp.conversion;

public abstract class AbstractOneWayConverter<T, U> extends AbstractConverter<T, U> {

    public T convertTwoToOne(U value, Class<? extends T> toType) {
        throw new UnsupportedConversionException(value, toType);
    }

}
