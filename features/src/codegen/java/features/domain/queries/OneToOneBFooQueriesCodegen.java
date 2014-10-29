package features.domain.queries;

import features.domain.OneToOneBFoo;
import joist.domain.AbstractQueries;

abstract class OneToOneBFooQueriesCodegen extends AbstractQueries<OneToOneBFoo> {

  public OneToOneBFooQueriesCodegen() {
    super(OneToOneBFoo.class);
  }

  public void delete(OneToOneBFoo instance) {
    super.delete(instance);
  }

}
