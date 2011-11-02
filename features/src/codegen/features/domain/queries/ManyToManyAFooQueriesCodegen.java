package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.ManyToManyAFoo;

public abstract class ManyToManyAFooQueriesCodegen extends AbstractQueries<ManyToManyAFoo> {

  public ManyToManyAFooQueriesCodegen() {
    super(ManyToManyAFoo.class);
  }

  public void delete(ManyToManyAFoo instance) {
    super.delete(instance);
  }

}
