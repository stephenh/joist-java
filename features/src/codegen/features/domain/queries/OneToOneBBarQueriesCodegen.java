package features.domain.queries;

import features.domain.OneToOneBBar;
import joist.domain.AbstractQueries;

public abstract class OneToOneBBarQueriesCodegen extends AbstractQueries<OneToOneBBar> {

    public OneToOneBBarQueriesCodegen() {
        super(OneToOneBBar.class);
    }

    public void delete(OneToOneBBar instance) {
        super.delete(instance);
    }

}
