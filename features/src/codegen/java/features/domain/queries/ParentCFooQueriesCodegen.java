package features.domain.queries;

import features.domain.ParentCFoo;
import joist.domain.AbstractQueries;

abstract class ParentCFooQueriesCodegen extends AbstractQueries<ParentCFoo> {

  public ParentCFooQueriesCodegen() {
    super(ParentCFoo.class);
  }

  public void delete(ParentCFoo instance) {
    super.delete(instance);
  }

}
