package features.domain.queries;

import features.domain.Primitives;
import joist.domain.AbstractQueries;

public abstract class PrimitivesQueriesCodegen extends AbstractQueries<Primitives> {

    public PrimitivesQueriesCodegen() {
        super(Primitives.class);
    }

    public void delete(Primitives instance) {
        super.delete(instance);
    }

}
