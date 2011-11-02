package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.OneToOneBFoo;

public abstract class OneToOneBFooQueriesCodegen extends AbstractQueries<OneToOneBFoo> {

  public OneToOneBFooQueriesCodegen() {
    super(OneToOneBFoo.class);
  }

  public void delete(OneToOneBFoo instance) {
    super.delete(instance);
  }

}
