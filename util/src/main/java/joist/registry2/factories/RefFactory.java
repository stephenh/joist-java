package joist.registry2.factories;

public interface RefFactory<T> {

  T create();

  void destroy(T resource);

}
