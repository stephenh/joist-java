package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.OneToOneAFoo;

public abstract class OneToOneAFooQueriesCodegen extends AbstractQueries<OneToOneAFoo> {

  public OneToOneAFooQueriesCodegen() {
    super(OneToOneAFoo.class);
  }

  public void delete(OneToOneAFoo instance) {
    super.delete(instance);
  }

}
