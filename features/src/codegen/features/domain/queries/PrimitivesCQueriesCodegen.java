package features.domain.queries;

import features.domain.PrimitivesC;
import joist.domain.AbstractQueries;

public abstract class PrimitivesCQueriesCodegen extends AbstractQueries<PrimitivesC> {

  public PrimitivesCQueriesCodegen() {
    super(PrimitivesC.class);
  }

  public void delete(PrimitivesC instance) {
    super.delete(instance);
  }

}
