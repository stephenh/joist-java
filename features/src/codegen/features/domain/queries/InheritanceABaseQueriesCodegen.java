package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.InheritanceABase;

public abstract class InheritanceABaseQueriesCodegen extends AbstractQueries<InheritanceABase> {

  public InheritanceABaseQueriesCodegen() {
    super(InheritanceABase.class);
  }

  public void delete(InheritanceABase instance) {
    super.delete(instance);
  }

}
