package features.domain.queries;

import features.domain.OneToOneABar;
import joist.domain.AbstractQueries;

abstract class OneToOneABarQueriesCodegen extends AbstractQueries<OneToOneABar> {

  public OneToOneABarQueriesCodegen() {
    super(OneToOneABar.class);
  }

  public void delete(OneToOneABar instance) {
    super.delete(instance);
  }

}
