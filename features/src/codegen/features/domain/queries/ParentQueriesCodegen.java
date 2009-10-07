package features.domain.queries;

import features.domain.Child;
import features.domain.Parent;
import joist.domain.AbstractQueries;
import joist.util.Copy;

public abstract class ParentQueriesCodegen extends AbstractQueries<Parent> {

    public ParentQueriesCodegen() {
        super(Parent.class);
    }

    public void delete(Parent instance) {
        for (Child o : Copy.list(instance.getChilds())) {
            Child.queries.delete(o);
        }
        super.delete(instance);
    }

}
