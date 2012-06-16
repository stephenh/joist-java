package features.domain.queries;

import features.domain.InheritanceABase;
import joist.domain.AbstractQueries;

abstract class InheritanceABaseQueriesCodegen extends AbstractQueries<InheritanceABase> {

  public InheritanceABaseQueriesCodegen() {
    super(InheritanceABase.class);
  }

  public void delete(InheritanceABase instance) {
    super.delete(instance);
  }

}
