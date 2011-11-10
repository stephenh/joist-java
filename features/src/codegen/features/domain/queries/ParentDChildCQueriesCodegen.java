package features.domain.queries;

import features.domain.ParentDChildC;
import joist.domain.AbstractQueries;

public abstract class ParentDChildCQueriesCodegen extends AbstractQueries<ParentDChildC> {

  public ParentDChildCQueriesCodegen() {
    super(ParentDChildC.class);
  }

  public void delete(ParentDChildC instance) {
    super.delete(instance);
  }

}
