package features.domain.queries;

import features.domain.ManyToManyAFooToBar;
import joist.domain.AbstractQueries;

public abstract class ManyToManyAFooToBarQueriesCodegen extends AbstractQueries<ManyToManyAFooToBar> {

    public ManyToManyAFooToBarQueriesCodegen() {
        super(ManyToManyAFooToBar.class);
    }

    public void delete(ManyToManyAFooToBar instance) {
        super.delete(instance);
    }

}
