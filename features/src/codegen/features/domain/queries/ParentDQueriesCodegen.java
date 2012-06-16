package features.domain.queries;

import features.domain.ParentD;
import joist.domain.AbstractQueries;

abstract class ParentDQueriesCodegen extends AbstractQueries<ParentD> {

  public ParentDQueriesCodegen() {
    super(ParentD.class);
  }

  public void delete(ParentD instance) {
    super.delete(instance);
  }

}
