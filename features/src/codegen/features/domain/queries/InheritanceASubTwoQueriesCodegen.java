package features.domain.queries;

import features.domain.InheritanceABase;
import features.domain.InheritanceASubTwo;
import joist.domain.AbstractQueries;

public abstract class InheritanceASubTwoQueriesCodegen extends AbstractQueries<InheritanceASubTwo> {

    public InheritanceASubTwoQueriesCodegen() {
        super(InheritanceASubTwo.class);
    }

    public void delete(InheritanceASubTwo instance) {
        InheritanceABase.queries.delete(instance);
    }

}
