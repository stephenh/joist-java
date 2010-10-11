package features.domain.queries;

import features.domain.InheritanceC;
import features.domain.InheritanceCFoo2;
import joist.domain.AbstractQueries;

public abstract class InheritanceCFoo2QueriesCodegen extends AbstractQueries<InheritanceCFoo2> {

    public InheritanceCFoo2QueriesCodegen() {
        super(InheritanceCFoo2.class);
    }

    public void delete(InheritanceCFoo2 instance) {
        InheritanceC.queries.delete(instance);
    }

}
