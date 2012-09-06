package features.domain.queries;

import features.domain.ChildG;
import joist.domain.AbstractQueries;

abstract class ChildGQueriesCodegen extends AbstractQueries<ChildG> {

  public ChildGQueriesCodegen() {
    super(ChildG.class);
  }

  public void delete(ChildG instance) {
    super.delete(instance);
  }

}
