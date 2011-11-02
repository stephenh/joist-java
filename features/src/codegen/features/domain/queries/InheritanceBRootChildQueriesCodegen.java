package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.InheritanceBRootChild;

public abstract class InheritanceBRootChildQueriesCodegen extends AbstractQueries<InheritanceBRootChild> {

  public InheritanceBRootChildQueriesCodegen() {
    super(InheritanceBRootChild.class);
  }

  public void delete(InheritanceBRootChild instance) {
    super.delete(instance);
  }

}
