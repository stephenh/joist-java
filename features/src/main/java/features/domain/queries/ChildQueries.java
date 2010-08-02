package features.domain.queries;

import java.util.List;

import joist.domain.orm.queries.Select;
import features.domain.Child;
import features.domain.ChildAlias;
import features.domain.ParentAlias;

public class ChildQueries extends ChildQueriesCodegen {

  public List<Child> findForParentOfName(String name) {
    // SELECT * FROM child c
    // INNER JOIN parent p ON p.id = c.parent_id
    // WHERE p.name = :name
    // ORDER BY c.name

    ChildAlias c = new ChildAlias("c");
    ParentAlias p = new ParentAlias("p");
    Select<Child> q = Select.from(c);
    q.join(p.on(c.parent));
    q.where(p.name.eq(name));
    q.orderBy(c.name.asc());
    return q.list();
  }

}
