package features.domain.queries;

import features.domain.Child;
import joist.domain.AbstractQueries;

abstract class ChildQueriesCodegen extends AbstractQueries<Child> {

  public ChildQueriesCodegen() {
    super(Child.class);
  }

  public void delete(Child instance) {
    super.delete(instance);
  }

}
