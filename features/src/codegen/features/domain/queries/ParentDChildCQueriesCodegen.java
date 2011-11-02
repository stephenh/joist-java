package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.ParentDChildC;

public abstract class ParentDChildCQueriesCodegen extends AbstractQueries<ParentDChildC> {

  public ParentDChildCQueriesCodegen() {
    super(ParentDChildC.class);
  }

  public void delete(ParentDChildC instance) {
    super.delete(instance);
  }

}
