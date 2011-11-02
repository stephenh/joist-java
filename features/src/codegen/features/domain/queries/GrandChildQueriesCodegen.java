package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.GrandChild;

public abstract class GrandChildQueriesCodegen extends AbstractQueries<GrandChild> {

  public GrandChildQueriesCodegen() {
    super(GrandChild.class);
  }

  public void delete(GrandChild instance) {
    super.delete(instance);
  }

}
