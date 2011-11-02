package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.ParentDChildA;

public abstract class ParentDChildAQueriesCodegen extends AbstractQueries<ParentDChildA> {

  public ParentDChildAQueriesCodegen() {
    super(ParentDChildA.class);
  }

  public void delete(ParentDChildA instance) {
    super.delete(instance);
  }

}
