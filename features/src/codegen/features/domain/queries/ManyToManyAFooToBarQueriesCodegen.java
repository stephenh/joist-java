package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.ManyToManyAFooToBar;

public abstract class ManyToManyAFooToBarQueriesCodegen extends AbstractQueries<ManyToManyAFooToBar> {

  public ManyToManyAFooToBarQueriesCodegen() {
    super(ManyToManyAFooToBar.class);
  }

  public void delete(ManyToManyAFooToBar instance) {
    super.delete(instance);
  }

}
