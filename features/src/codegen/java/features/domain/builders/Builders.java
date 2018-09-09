package features.domain.builders;

import features.domain.Primitives;

public class Builders {

  public static PrimitivesBuilder aPrimitives() {
    return new PrimitivesBuilder(new Primitives());
  }

  public static PrimitivesBuilder existing(Primitives primitives) {
    return new PrimitivesBuilder(primitives);
  }

  public static PrimitivesBuilder thePrimitives(long id) {
    return new PrimitivesBuilder(Primitives.queries.find(id));
  }

  public static PrimitivesBuilder thePrimitives(int id) {
    return new PrimitivesBuilder(Primitives.queries.find((long) id));
  }

}
