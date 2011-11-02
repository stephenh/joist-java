package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.InheritanceC;
import features.domain.InheritanceCFoo2;

public abstract class InheritanceCFoo2QueriesCodegen extends AbstractQueries<InheritanceCFoo2> {

  public InheritanceCFoo2QueriesCodegen() {
    super(InheritanceCFoo2.class);
  }

  public void delete(InheritanceCFoo2 instance) {
    InheritanceC.queries.delete(instance);
  }

}
