package features.domain.queries;

import features.domain.OneToOneABar;
import features.domain.OneToOneAFoo;
import joist.domain.AbstractQueries;

public abstract class OneToOneAFooQueriesCodegen extends AbstractQueries<OneToOneAFoo> {

    public OneToOneAFooQueriesCodegen() {
        super(OneToOneAFoo.class);
    }

    public void delete(OneToOneAFoo instance) {
        OneToOneABar.queries.delete(instance.getOneToOneABar());
        super.delete(instance);
    }

}
