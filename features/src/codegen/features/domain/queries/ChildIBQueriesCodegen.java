package features.domain.queries;

import features.domain.ChildIB;
import joist.domain.AbstractQueries;

abstract class ChildIBQueriesCodegen extends AbstractQueries<ChildIB> {

  public ChildIBQueriesCodegen() {
    super(ChildIB.class);
  }

  public void delete(ChildIB instance) {
    super.delete(instance);
  }

}
