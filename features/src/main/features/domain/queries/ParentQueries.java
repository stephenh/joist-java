package features.domain.queries;

import java.util.List;

import joist.domain.AbstractQueries;
import joist.domain.orm.queries.PagedList;
import joist.domain.orm.queries.Select;
import features.domain.ChildAlias;
import features.domain.Parent;
import features.domain.ParentAlias;

public class ParentQueries extends AbstractQueries<Parent> {

    public ParentQueries() {
        super(Parent.class);
    }

    public List<Parent> all(int offset, int limit) {
        ParentAlias p = new ParentAlias("p");
        return Select.from(p).offset(offset).limit(limit).orderBy(p.id.asc()).list();
    }

    public PagedList<Parent> allPaged() {
        ParentAlias p = new ParentAlias("p");
        return Select.from(p).orderBy(p.id.asc()).paged();
    }

    public List<Parent> findWithoutChildFetch() {
        ParentAlias p = new ParentAlias("p");
        ChildAlias c = new ChildAlias("c");

        Select<Parent> q = Select.from(p);
        q.join(c.parent.on(p));
        return Select.from(p).list();
    }

    public List<Parent> findWithChildFetch() {
        ParentAlias p = new ParentAlias("p");
        ChildAlias c = new ChildAlias("c");

        Select<Parent> q = Select.from(p);
        q.join(c.parent.on(p));
        return q.list();
    }

}
