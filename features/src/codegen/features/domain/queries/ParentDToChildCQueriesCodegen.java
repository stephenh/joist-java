package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.ParentDToChildC;

public abstract class ParentDToChildCQueriesCodegen extends AbstractQueries<ParentDToChildC> {

  public ParentDToChildCQueriesCodegen() {
    super(ParentDToChildC.class);
  }

  public void delete(ParentDToChildC instance) {
    super.delete(instance);
  }

}
