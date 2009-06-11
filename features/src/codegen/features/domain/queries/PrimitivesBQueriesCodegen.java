package features.domain.queries;

import features.domain.PrimitivesB;
import joist.domain.AbstractQueries;

public abstract class PrimitivesBQueriesCodegen extends AbstractQueries<PrimitivesB> {

    public PrimitivesBQueriesCodegen() {
        super(PrimitivesB.class);
    }

    public void delete(PrimitivesB instance) {
        super.delete(instance);
    }

}
