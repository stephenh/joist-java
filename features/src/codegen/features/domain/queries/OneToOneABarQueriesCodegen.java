package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.OneToOneABar;

public abstract class OneToOneABarQueriesCodegen extends AbstractQueries<OneToOneABar> {

  public OneToOneABarQueriesCodegen() {
    super(OneToOneABar.class);
  }

  public void delete(OneToOneABar instance) {
    super.delete(instance);
  }

}
