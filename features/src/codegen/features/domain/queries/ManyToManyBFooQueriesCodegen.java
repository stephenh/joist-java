package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.ManyToManyBFoo;

public abstract class ManyToManyBFooQueriesCodegen extends AbstractQueries<ManyToManyBFoo> {

  public ManyToManyBFooQueriesCodegen() {
    super(ManyToManyBFoo.class);
  }

  public void delete(ManyToManyBFoo instance) {
    super.delete(instance);
  }

}
