package joist.util;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class ConstructorTypeParameter {

  private final Object instance;
  private List<Param> possible = new ArrayList<Param>();

  public static ConstructorTypeParameter of(Object instance) {
    return new ConstructorTypeParameter(instance);
  }

  protected ConstructorTypeParameter(Object instance) {
    this.instance = instance;
  }

  public ConstructorTypeParameter param(Class<?> superclass, int index) {
    this.possible.add(new Param(superclass, index));
    return this;
  }

  public Class<?> resolve() {
    for (Class<?> current = this.instance.getClass(); current != null; current = current.getSuperclass()) {
      for (Param p : this.possible) {
        if (current.getSuperclass().equals(p.superclass)) {
          ParameterizedType ptype = (ParameterizedType) current.getGenericSuperclass();
          return (Class<?>) ptype.getActualTypeArguments()[p.index];
        }
      }
    }
    throw new RuntimeException("Could not resolve for " + this.instance);
  }

  public static class Param {
    public final Class<?> superclass;
    public final int index;

    public Param(Class<?> superclass, int index) {
      this.superclass = superclass;
      this.index = index;
    }
  }

}
