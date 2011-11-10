package features.domain.queries;

import features.domain.ParentDToChildC;
import joist.domain.AbstractQueries;

public abstract class ParentDToChildCQueriesCodegen extends AbstractQueries<ParentDToChildC> {

  public ParentDToChildCQueriesCodegen() {
    super(ParentDToChildC.class);
  }

  public void delete(ParentDToChildC instance) {
    super.delete(instance);
  }

}
