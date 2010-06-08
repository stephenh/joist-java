package features.domain.queries;

import features.domain.ParentDChildA;
import joist.domain.AbstractQueries;

public abstract class ParentDChildAQueriesCodegen extends AbstractQueries<ParentDChildA> {

    public ParentDChildAQueriesCodegen() {
        super(ParentDChildA.class);
    }

    public void delete(ParentDChildA instance) {
        super.delete(instance);
    }

}
