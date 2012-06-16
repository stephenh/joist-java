package features.domain.queries;

import features.domain.ParentCBar;
import joist.domain.AbstractQueries;

abstract class ParentCBarQueriesCodegen extends AbstractQueries<ParentCBar> {

  public ParentCBarQueriesCodegen() {
    super(ParentCBar.class);
  }

  public void delete(ParentCBar instance) {
    super.delete(instance);
  }

}
