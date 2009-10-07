package features.domain.queries;

import features.domain.ParentBChildBar;
import joist.domain.AbstractQueries;

public abstract class ParentBChildBarQueriesCodegen extends AbstractQueries<ParentBChildBar> {

    public ParentBChildBarQueriesCodegen() {
        super(ParentBChildBar.class);
    }

    public void delete(ParentBChildBar instance) {
        super.delete(instance);
    }

}
