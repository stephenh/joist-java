package features.domain.queries;

import features.domain.ChildH;
import joist.domain.AbstractQueries;

abstract class ChildHQueriesCodegen extends AbstractQueries<ChildH> {

  public ChildHQueriesCodegen() {
    super(ChildH.class);
  }

  public void delete(ChildH instance) {
    super.delete(instance);
  }

}
