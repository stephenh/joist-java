package features.domain.queries;

import features.domain.ManyToManyAFoo;
import joist.domain.AbstractQueries;

public abstract class ManyToManyAFooQueriesCodegen extends AbstractQueries<ManyToManyAFoo> {

    public ManyToManyAFooQueriesCodegen() {
        super(ManyToManyAFoo.class);
    }

    public void delete(ManyToManyAFoo instance) {
        super.delete(instance);
    }

}
