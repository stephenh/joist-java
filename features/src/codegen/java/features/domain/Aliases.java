package features.domain;

import joist.domain.orm.AliasRegistry;

public class Aliases {

  private static ChildAlias child;
  private static GrandChildAlias grandChild;
  private static ParentAlias parent;
  private static PrimitivesAlias primitives;

  public static ChildAlias child() {
    if (child == null) {
      child = new ChildAlias();
      AliasRegistry.register(Child.class, child);
    }
    return child;
  }

  public static GrandChildAlias grandChild() {
    if (grandChild == null) {
      grandChild = new GrandChildAlias();
      AliasRegistry.register(GrandChild.class, grandChild);
    }
    return grandChild;
  }

  public static ParentAlias parent() {
    if (parent == null) {
      parent = new ParentAlias();
      AliasRegistry.register(Parent.class, parent);
    }
    return parent;
  }

  public static PrimitivesAlias primitives() {
    if (primitives == null) {
      primitives = new PrimitivesAlias();
      AliasRegistry.register(Primitives.class, primitives);
    }
    return primitives;
  }

}
