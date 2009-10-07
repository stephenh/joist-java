package features.domain.queries;

import features.domain.InheritanceBRootChild;
import joist.domain.AbstractQueries;

public abstract class InheritanceBRootChildQueriesCodegen extends AbstractQueries<InheritanceBRootChild> {

    public InheritanceBRootChildQueriesCodegen() {
        super(InheritanceBRootChild.class);
    }

    public void delete(InheritanceBRootChild instance) {
        super.delete(instance);
    }

}
