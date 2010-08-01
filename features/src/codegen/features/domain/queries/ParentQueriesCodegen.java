package features.domain.queries;

import features.domain.Parent;
import joist.domain.AbstractQueries;

public abstract class ParentQueriesCodegen extends AbstractQueries<Parent> {

    public ParentQueriesCodegen() {
        super(Parent.class);
    }

    public void delete(Parent instance) {
        super.delete(instance);
    }

}
