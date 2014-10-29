package features.domain.queries;

import features.domain.InheritanceAThing;
import joist.domain.AbstractQueries;

abstract class InheritanceAThingQueriesCodegen extends AbstractQueries<InheritanceAThing> {

  public InheritanceAThingQueriesCodegen() {
    super(InheritanceAThing.class);
  }

  public void delete(InheritanceAThing instance) {
    super.delete(instance);
  }

}
