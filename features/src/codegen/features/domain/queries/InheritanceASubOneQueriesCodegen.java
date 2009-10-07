package features.domain.queries;

import features.domain.InheritanceABase;
import features.domain.InheritanceASubOne;
import joist.domain.AbstractQueries;

public abstract class InheritanceASubOneQueriesCodegen extends AbstractQueries<InheritanceASubOne> {

    public InheritanceASubOneQueriesCodegen() {
        super(InheritanceASubOne.class);
    }

    public void delete(InheritanceASubOne instance) {
        InheritanceABase.queries.delete(instance);
    }

}
