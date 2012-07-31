package features.domain.queries;

import features.domain.ValuesB;
import joist.domain.AbstractQueries;

abstract class ValuesBQueriesCodegen extends AbstractQueries<ValuesB> {

  public ValuesBQueriesCodegen() {
    super(ValuesB.class);
  }

  public void delete(ValuesB instance) {
    super.delete(instance);
  }

}
