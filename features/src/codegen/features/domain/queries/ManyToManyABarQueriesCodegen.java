package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.ManyToManyABar;

public abstract class ManyToManyABarQueriesCodegen extends AbstractQueries<ManyToManyABar> {

  public ManyToManyABarQueriesCodegen() {
    super(ManyToManyABar.class);
  }

  public void delete(ManyToManyABar instance) {
    super.delete(instance);
  }

}
