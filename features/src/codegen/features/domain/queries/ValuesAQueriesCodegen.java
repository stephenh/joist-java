package features.domain.queries;

import features.domain.ValuesA;
import joist.domain.AbstractQueries;

abstract class ValuesAQueriesCodegen extends AbstractQueries<ValuesA> {

  public ValuesAQueriesCodegen() {
    super(ValuesA.class);
  }

  public void delete(ValuesA instance) {
    super.delete(instance);
  }

}
