package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.Child;

public abstract class ChildQueriesCodegen extends AbstractQueries<Child> {

  public ChildQueriesCodegen() {
    super(Child.class);
  }

  public void delete(Child instance) {
    super.delete(instance);
  }

}
