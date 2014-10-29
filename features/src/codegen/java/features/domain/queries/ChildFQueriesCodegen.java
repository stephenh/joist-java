package features.domain.queries;

import features.domain.ChildF;
import joist.domain.AbstractQueries;

abstract class ChildFQueriesCodegen extends AbstractQueries<ChildF> {

  public ChildFQueriesCodegen() {
    super(ChildF.class);
  }

  public void delete(ChildF instance) {
    super.delete(instance);
  }

}
