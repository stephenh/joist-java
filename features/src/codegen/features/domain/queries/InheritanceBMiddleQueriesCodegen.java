package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.InheritanceBMiddle;
import features.domain.InheritanceBRoot;

public abstract class InheritanceBMiddleQueriesCodegen extends AbstractQueries<InheritanceBMiddle> {

  public InheritanceBMiddleQueriesCodegen() {
    super(InheritanceBMiddle.class);
  }

  public void delete(InheritanceBMiddle instance) {
    InheritanceBRoot.queries.delete(instance);
  }

}
