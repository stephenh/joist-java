package features.domain.queries;

import features.domain.ChildIA;
import joist.domain.AbstractQueries;

abstract class ChildIAQueriesCodegen extends AbstractQueries<ChildIA> {

  public ChildIAQueriesCodegen() {
    super(ChildIA.class);
  }

  public void delete(ChildIA instance) {
    super.delete(instance);
  }

}
