package features.domain.queries;

import features.domain.InheritanceBMiddle;
import features.domain.InheritanceBRoot;
import joist.domain.AbstractQueries;

public abstract class InheritanceBMiddleQueriesCodegen extends AbstractQueries<InheritanceBMiddle> {

    public InheritanceBMiddleQueriesCodegen() {
        super(InheritanceBMiddle.class);
    }

    public void delete(InheritanceBMiddle instance) {
        InheritanceBRoot.queries.delete(instance);
    }

}
