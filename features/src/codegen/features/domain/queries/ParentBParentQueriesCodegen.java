package features.domain.queries;

import features.domain.ParentBChildBar;
import features.domain.ParentBChildFoo;
import features.domain.ParentBParent;
import joist.domain.AbstractQueries;
import joist.util.Copy;

public abstract class ParentBParentQueriesCodegen extends AbstractQueries<ParentBParent> {

    public ParentBParentQueriesCodegen() {
        super(ParentBParent.class);
    }

    public void delete(ParentBParent instance) {
        for (ParentBChildBar o : Copy.list(instance.getParentBChildBars())) {
            ParentBChildBar.queries.delete(o);
        }
        for (ParentBChildFoo o : Copy.list(instance.getParentBChildFoos())) {
            ParentBChildFoo.queries.delete(o);
        }
        super.delete(instance);
    }

}
