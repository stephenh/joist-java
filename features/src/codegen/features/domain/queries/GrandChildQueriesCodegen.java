package features.domain.queries;

import features.domain.GrandChild;
import joist.domain.AbstractQueries;

abstract class GrandChildQueriesCodegen extends AbstractQueries<GrandChild> {

  public GrandChildQueriesCodegen() {
    super(GrandChild.class);
  }

  public void delete(GrandChild instance) {
    super.delete(instance);
  }

}
