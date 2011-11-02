package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.InheritanceAThing;

public abstract class InheritanceAThingQueriesCodegen extends AbstractQueries<InheritanceAThing> {

  public InheritanceAThingQueriesCodegen() {
    super(InheritanceAThing.class);
  }

  public void delete(InheritanceAThing instance) {
    super.delete(instance);
  }

}
