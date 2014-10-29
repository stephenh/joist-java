package features.domain.queries;

import features.domain.ChildIA;
import features.domain.ChildIB;
import features.domain.ParentI;
import joist.domain.AbstractQueries;
import joist.util.Copy;

abstract class ParentIQueriesCodegen extends AbstractQueries<ParentI> {

  public ParentIQueriesCodegen() {
    super(ParentI.class);
  }

  public void delete(ParentI instance) {
    for (ChildIA o : Copy.list(instance.getChildAs())) {
      ChildIA.queries.delete(o);
    }
    if (instance.getChildB() != null) {
      ChildIB.queries.delete(instance.getChildB());
    }
    super.delete(instance);
  }

}
