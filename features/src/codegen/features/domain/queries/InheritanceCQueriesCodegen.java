package features.domain.queries;

import features.domain.InheritanceC;
import joist.domain.AbstractQueries;

abstract class InheritanceCQueriesCodegen extends AbstractQueries<InheritanceC> {

  public InheritanceCQueriesCodegen() {
    super(InheritanceC.class);
  }

  public void delete(InheritanceC instance) {
    super.delete(instance);
  }

}
