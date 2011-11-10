package features.domain.queries;

import features.domain.ManyToManyABar;
import joist.domain.AbstractQueries;

public abstract class ManyToManyABarQueriesCodegen extends AbstractQueries<ManyToManyABar> {

  public ManyToManyABarQueriesCodegen() {
    super(ManyToManyABar.class);
  }

  public void delete(ManyToManyABar instance) {
    super.delete(instance);
  }

}
