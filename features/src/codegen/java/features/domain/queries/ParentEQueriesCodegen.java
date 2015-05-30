package features.domain.queries;

import features.domain.ParentE;
import joist.domain.AbstractQueries;

abstract class ParentEQueriesCodegen extends AbstractQueries<ParentE> {

  public ParentEQueriesCodegen() {
    super(ParentE.class);
  }

  public void delete(ParentE instance) {
    super.delete(instance);
  }

}
