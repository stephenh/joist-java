package features.domain.queries;

import features.domain.InheritanceBRoot;
import features.domain.InheritanceBRootChild;
import joist.domain.AbstractQueries;
import joist.util.Copy;

public abstract class InheritanceBRootQueriesCodegen extends AbstractQueries<InheritanceBRoot> {

    public InheritanceBRootQueriesCodegen() {
        super(InheritanceBRoot.class);
    }

    public void delete(InheritanceBRoot instance) {
        for (InheritanceBRootChild o : Copy.list(instance.getInheritanceBRootChilds())) {
            InheritanceBRootChild.queries.delete(o);
        }
        super.delete(instance);
    }

}
