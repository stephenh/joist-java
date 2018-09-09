package features.domain;

import joist.domain.orm.AliasRegistry;

public class Aliases {

  private static PrimitivesAlias primitives;

  public static PrimitivesAlias primitives() {
    if (primitives == null) {
      primitives = new PrimitivesAlias();
      AliasRegistry.register(Primitives.class, primitives);
    }
    return primitives;
  }

}
