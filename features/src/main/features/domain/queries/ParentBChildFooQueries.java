package features.domain.queries;

import java.util.List;

import joist.domain.AbstractQueries;
import joist.domain.queries.Select;
import features.domain.ParentBChildFoo;
import features.domain.ParentBChildFooAlias;
import features.domain.ParentBParentAlias;

public class ParentBChildFooQueries extends AbstractQueries<ParentBChildFoo> {

    public ParentBChildFooQueries() {
        super(ParentBChildFoo.class);
    }

    public List<ParentBChildFoo> findByParentName(String name) {
        ParentBParentAlias p = new ParentBParentAlias("p");
        ParentBChildFooAlias f = new ParentBChildFooAlias("f");

        Select<ParentBChildFoo> q = Select.from(f);
        q.join(p.on(f.parentBParent));
        q.where(p.name.equals(name));
        return q.list();
    }

}
