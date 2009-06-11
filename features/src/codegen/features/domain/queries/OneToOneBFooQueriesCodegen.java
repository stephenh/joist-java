package features.domain.queries;

import features.domain.OneToOneBBar;
import features.domain.OneToOneBFoo;
import joist.domain.AbstractQueries;
import joist.util.Copy;

public abstract class OneToOneBFooQueriesCodegen extends AbstractQueries<OneToOneBFoo> {

    public OneToOneBFooQueriesCodegen() {
        super(OneToOneBFoo.class);
    }

    public void delete(OneToOneBFoo instance) {
        for (OneToOneBBar o : Copy.list(instance.getOneToOneBBars())) {
            OneToOneBBar.queries.delete(o);
        }
        super.delete(instance);
    }

}
