package features.domain.mappers;

import java.util.List;

import org.exigencecorp.domainobjects.queries.Select;

import features.domain.Child;
import features.domain.Parent;

public class ChildMapper {

    public ChildMapper() {
    }

    public Child find(int id) {
        ChildAlias c = new ChildAlias("c");
        Select<Child> q = Select.from(c);
        q.where(c.id.equals(id));
        return q.unique();
    }

    public List<Child> findForParent(Parent parent) {
        ChildAlias c = new ChildAlias("c");
        Select<Child> q = Select.from(c);
        q.where(c.parent.equals(parent.getId().intValue()));
        q.orderBy(c.id.asc());
        return q.list();
    }

    public List<Child> findForParentOfName(String name) {
        // SELECT * FROM child c
        // INNER JOIN parent p ON p.id = c.parent_id
        // WHERE p.name = :name

        ChildAlias c = new ChildAlias("c");
        ParentAlias p = new ParentAlias("p");
        Select<Child> q = Select.from(c);
        q.join(p.on(c.parent));
        q.where(p.name.equals(name));
        q.orderBy(c.name.asc());
        return q.list();
    }

}
