package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.PrimitivesB;

public abstract class PrimitivesBQueriesCodegen extends AbstractQueries<PrimitivesB> {

  public PrimitivesBQueriesCodegen() {
    super(PrimitivesB.class);
  }

  public void delete(PrimitivesB instance) {
    super.delete(instance);
  }

}
