package features.domain.queries;

import features.domain.OneToOneABar;
import joist.domain.AbstractQueries;

public abstract class OneToOneABarQueriesCodegen extends AbstractQueries<OneToOneABar> {

    public OneToOneABarQueriesCodegen() {
        super(OneToOneABar.class);
    }

    public void delete(OneToOneABar instance) {
        super.delete(instance);
    }

}
