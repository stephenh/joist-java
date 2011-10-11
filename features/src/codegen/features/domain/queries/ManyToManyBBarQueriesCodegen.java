package features.domain.queries;

import features.domain.ManyToManyBBar;
import joist.domain.AbstractQueries;

public abstract class ManyToManyBBarQueriesCodegen extends AbstractQueries<ManyToManyBBar> {

  public ManyToManyBBarQueriesCodegen() {
    super(ManyToManyBBar.class);
  }

  public void delete(ManyToManyBBar instance) {
    super.delete(instance);
  }

}
