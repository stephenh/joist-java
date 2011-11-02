package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.ManyToManyBBar;

public abstract class ManyToManyBBarQueriesCodegen extends AbstractQueries<ManyToManyBBar> {

  public ManyToManyBBarQueriesCodegen() {
    super(ManyToManyBBar.class);
  }

  public void delete(ManyToManyBBar instance) {
    super.delete(instance);
  }

}
