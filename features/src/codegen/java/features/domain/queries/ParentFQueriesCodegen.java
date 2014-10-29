package features.domain.queries;

import features.domain.ChildF;
import features.domain.ParentF;
import joist.domain.AbstractQueries;

abstract class ParentFQueriesCodegen extends AbstractQueries<ParentF> {

  public ParentFQueriesCodegen() {
    super(ParentF.class);
  }

  public void delete(ParentF instance) {
    if (instance.getChildOne() != null) {
      ChildF.queries.delete(instance.getChildOne());
    }
    if (instance.getChildTwo() != null) {
      ChildF.queries.delete(instance.getChildTwo());
    }
    super.delete(instance);
  }

}
