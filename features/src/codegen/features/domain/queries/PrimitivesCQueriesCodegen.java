package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.PrimitivesC;

public abstract class PrimitivesCQueriesCodegen extends AbstractQueries<PrimitivesC> {

  public PrimitivesCQueriesCodegen() {
    super(PrimitivesC.class);
  }

  public void delete(PrimitivesC instance) {
    super.delete(instance);
  }

}
