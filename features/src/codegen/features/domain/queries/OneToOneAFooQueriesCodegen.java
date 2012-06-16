package features.domain.queries;

import features.domain.OneToOneAFoo;
import joist.domain.AbstractQueries;

abstract class OneToOneAFooQueriesCodegen extends AbstractQueries<OneToOneAFoo> {

  public OneToOneAFooQueriesCodegen() {
    super(OneToOneAFoo.class);
  }

  public void delete(OneToOneAFoo instance) {
    super.delete(instance);
  }

}
