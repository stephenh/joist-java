package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.ParentBChildFoo;

public abstract class ParentBChildFooQueriesCodegen extends AbstractQueries<ParentBChildFoo> {

  public ParentBChildFooQueriesCodegen() {
    super(ParentBChildFoo.class);
  }

  public void delete(ParentBChildFoo instance) {
    super.delete(instance);
  }

}
