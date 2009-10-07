package features.domain.queries;

import features.domain.ParentCBar;
import features.domain.ParentCFoo;
import joist.domain.AbstractQueries;
import joist.util.Copy;

public abstract class ParentCFooQueriesCodegen extends AbstractQueries<ParentCFoo> {

    public ParentCFooQueriesCodegen() {
        super(ParentCFoo.class);
    }

    public void delete(ParentCFoo instance) {
        for (ParentCBar o : Copy.list(instance.getFirstParentParentCBars())) {
            ParentCBar.queries.delete(o);
        }
        for (ParentCBar o : Copy.list(instance.getSecondParentParentCBars())) {
            ParentCBar.queries.delete(o);
        }
        super.delete(instance);
    }

}
