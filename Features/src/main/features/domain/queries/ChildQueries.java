package features.domain.queries;

import java.util.List;

import org.exigencecorp.domainobjects.AbstractQueries;
import org.exigencecorp.domainobjects.queries.Select;

import features.domain.Child;
import features.domain.ChildAlias;
import features.domain.ParentAlias;

public class ChildQueries extends AbstractQueries<Child> {

    public ChildQueries() {
        super(Child.class);
    }

    public List<Child> findForParentOfName(String name) {
        // SELECT * FROM child c
        // INNER JOIN parent p ON p.id = c.parent_id
        // WHERE p.name = :name
        // ORDER BY c.name

        ChildAlias c = new ChildAlias("c");
        ParentAlias p = new ParentAlias("p");
        Select<Child> q = Select.from(c);
        q.join(p.on(c.parent));
        q.where(p.name.equals(name));
        q.orderBy(c.name.asc());
        return q.list();
    }

}
