package features.domain.queries;

import features.domain.ManyToManyBFooToBar;
import joist.domain.AbstractQueries;

public abstract class ManyToManyBFooToBarQueriesCodegen extends AbstractQueries<ManyToManyBFooToBar> {

    public ManyToManyBFooToBarQueriesCodegen() {
        super(ManyToManyBFooToBar.class);
    }

    public void delete(ManyToManyBFooToBar instance) {
        super.delete(instance);
    }

}
