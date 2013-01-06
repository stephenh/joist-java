package features.domain.queries;

import features.domain.ChildH;
import features.domain.ParentH;
import joist.domain.AbstractQueries;
import joist.util.Copy;

abstract class ParentHQueriesCodegen extends AbstractQueries<ParentH> {

  public ParentHQueriesCodegen() {
    super(ParentH.class);
  }

  public void delete(ParentH instance) {
    for (ChildH o : Copy.list(instance.getParentChildHs())) {
      ChildH.queries.delete(o);
    }
    super.delete(instance);
  }

}
