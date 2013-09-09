package features.domain.queries;

import features.domain.InheritanceASubOneChild;
import joist.domain.AbstractQueries;

abstract class InheritanceASubOneChildQueriesCodegen extends AbstractQueries<InheritanceASubOneChild> {

  public InheritanceASubOneChildQueriesCodegen() {
    super(InheritanceASubOneChild.class);
  }

  public void delete(InheritanceASubOneChild instance) {
    super.delete(instance);
  }

}
