package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.InheritanceABase;
import features.domain.InheritanceASubOne;

public abstract class InheritanceASubOneQueriesCodegen extends AbstractQueries<InheritanceASubOne> {

  public InheritanceASubOneQueriesCodegen() {
    super(InheritanceASubOne.class);
  }

  public void delete(InheritanceASubOne instance) {
    InheritanceABase.queries.delete(instance);
  }

}
