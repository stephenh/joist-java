package features.domain.queries;

import features.domain.ManyToManyBFoo;
import joist.domain.AbstractQueries;

public abstract class ManyToManyBFooQueriesCodegen extends AbstractQueries<ManyToManyBFoo> {

  public ManyToManyBFooQueriesCodegen() {
    super(ManyToManyBFoo.class);
  }

  public void delete(ManyToManyBFoo instance) {
    super.delete(instance);
  }

}
