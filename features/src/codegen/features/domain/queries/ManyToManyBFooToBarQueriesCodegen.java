package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.ManyToManyBFooToBar;

public abstract class ManyToManyBFooToBarQueriesCodegen extends AbstractQueries<ManyToManyBFooToBar> {

  public ManyToManyBFooToBarQueriesCodegen() {
    super(ManyToManyBFooToBar.class);
  }

  public void delete(ManyToManyBFooToBar instance) {
    super.delete(instance);
  }

}
