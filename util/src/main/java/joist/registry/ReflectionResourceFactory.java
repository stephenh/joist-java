package joist.registry;

import joist.util.Reflection;

public class ReflectionResourceFactory<T> implements ResourceFactory<T> {

  private final Class<? extends T> type;

  public ReflectionResourceFactory(Class<? extends T> type) {
    this.type = type;
  }

  public T create() {
    return Reflection.newInstance(this.type);
  }

  public void destroy(T resource) {
  }

}
