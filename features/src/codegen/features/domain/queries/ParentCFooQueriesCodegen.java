package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.ParentCFoo;

public abstract class ParentCFooQueriesCodegen extends AbstractQueries<ParentCFoo> {

  public ParentCFooQueriesCodegen() {
    super(ParentCFoo.class);
  }

  public void delete(ParentCFoo instance) {
    super.delete(instance);
  }

}
