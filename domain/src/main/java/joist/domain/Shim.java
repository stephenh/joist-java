package joist.domain;

public interface Shim<T, U> {

  void set(T instance, U value);

  U get(T instance);

  String getName();

}
