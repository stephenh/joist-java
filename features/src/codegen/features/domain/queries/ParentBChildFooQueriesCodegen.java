package features.domain.queries;

import features.domain.ParentBChildFoo;
import joist.domain.AbstractQueries;

public abstract class ParentBChildFooQueriesCodegen extends AbstractQueries<ParentBChildFoo> {

    public ParentBChildFooQueriesCodegen() {
        super(ParentBChildFoo.class);
    }

    public void delete(ParentBChildFoo instance) {
        super.delete(instance);
    }

}
