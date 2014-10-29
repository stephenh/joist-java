package features.domain.queries;

import features.domain.ChildG;
import features.domain.ParentG;
import joist.domain.AbstractQueries;

abstract class ParentGQueriesCodegen extends AbstractQueries<ParentG> {

  public ParentGQueriesCodegen() {
    super(ParentG.class);
  }

  public void delete(ParentG instance) {
    if (instance.getParentOneChildG() != null) {
      ChildG.queries.delete(instance.getParentOneChildG());
    }
    if (instance.getParentTwoChildG() != null) {
      ChildG.queries.delete(instance.getParentTwoChildG());
    }
    super.delete(instance);
  }

}
