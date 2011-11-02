package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.ParentD;

public abstract class ParentDQueriesCodegen extends AbstractQueries<ParentD> {

  public ParentDQueriesCodegen() {
    super(ParentD.class);
  }

  public void delete(ParentD instance) {
    super.delete(instance);
  }

}
