package features.domain.queries;

import features.domain.InheritanceBBottom;
import features.domain.InheritanceBMiddle;
import joist.domain.AbstractQueries;

public abstract class InheritanceBBottomQueriesCodegen extends AbstractQueries<InheritanceBBottom> {

    public InheritanceBBottomQueriesCodegen() {
        super(InheritanceBBottom.class);
    }

    public void delete(InheritanceBBottom instance) {
        InheritanceBMiddle.queries.delete(instance);
    }

}
