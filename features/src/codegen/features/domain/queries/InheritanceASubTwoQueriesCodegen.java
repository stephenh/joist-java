package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.InheritanceABase;
import features.domain.InheritanceASubTwo;

public abstract class InheritanceASubTwoQueriesCodegen extends AbstractQueries<InheritanceASubTwo> {

  public InheritanceASubTwoQueriesCodegen() {
    super(InheritanceASubTwo.class);
  }

  public void delete(InheritanceASubTwo instance) {
    InheritanceABase.queries.delete(instance);
  }

}
