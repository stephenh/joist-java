package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.InheritanceBBottom;
import features.domain.InheritanceBMiddle;

public abstract class InheritanceBBottomQueriesCodegen extends AbstractQueries<InheritanceBBottom> {

  public InheritanceBBottomQueriesCodegen() {
    super(InheritanceBBottom.class);
  }

  public void delete(InheritanceBBottom instance) {
    InheritanceBMiddle.queries.delete(instance);
  }

}
