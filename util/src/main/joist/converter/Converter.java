package joist.converter;

public interface Converter<T, U> {

    Class<T> getTypeOne();

    Class<U> getTypeTwo();

    U convertOneToTwo(T value, Class<? extends U> toType);

    T convertTwoToOne(U value, Class<? extends T> toType);

    void setConverterRegistry(ConverterRegistry registry);

}
