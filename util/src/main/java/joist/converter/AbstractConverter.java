package joist.converter;

import joist.util.ConstructorTypeParameter;

public abstract class AbstractConverter<T, U> implements Converter<T, U> {

  private final Class<T> one;
  private final Class<U> two;
  protected ConverterRegistry registry;

  protected AbstractConverter() {
    this.one = (Class<T>) ConstructorTypeParameter.of(this).param(AbstractConverter.class, 0).param(AbstractOneWayConverter.class, 0).resolve();
    this.two = (Class<U>) ConstructorTypeParameter.of(this).param(AbstractConverter.class, 1).param(AbstractOneWayConverter.class, 1).resolve();
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
