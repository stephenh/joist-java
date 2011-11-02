package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.Parent;

public abstract class ParentQueriesCodegen extends AbstractQueries<Parent> {

  public ParentQueriesCodegen() {
    super(Parent.class);
  }

  public void delete(Parent instance) {
    super.delete(instance);
  }

}
