package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.InheritanceC;
import features.domain.InheritanceCFoo1;

public abstract class InheritanceCFoo1QueriesCodegen extends AbstractQueries<InheritanceCFoo1> {

  public InheritanceCFoo1QueriesCodegen() {
    super(InheritanceCFoo1.class);
  }

  public void delete(InheritanceCFoo1 instance) {
    InheritanceC.queries.delete(instance);
  }

}
