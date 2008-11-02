package features.domain.mappers;

import java.util.List;

import org.exigencecorp.domainobjects.queries.Select;

import features.domain.Child;

public class ChildMapper {

    public ChildMapper() {
    }

    public Child find(int id) {
        ChildAlias c = new ChildAlias("c");
        return Select.from(c).where(c.id.equals(id)).unique();
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
