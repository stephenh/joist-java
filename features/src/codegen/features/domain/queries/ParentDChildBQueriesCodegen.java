package features.domain.queries;

import features.domain.ParentDChildB;
import joist.domain.AbstractQueries;

abstract class ParentDChildBQueriesCodegen extends AbstractQueries<ParentDChildB> {

  public ParentDChildBQueriesCodegen() {
    super(ParentDChildB.class);
  }

  public void delete(ParentDChildB instance) {
    super.delete(instance);
  }

}
