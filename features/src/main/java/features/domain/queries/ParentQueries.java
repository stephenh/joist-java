package features.domain.queries;

import java.util.List;

import features.domain.ChildAlias;
import features.domain.Parent;
import features.domain.ParentAlias;
import joist.domain.orm.queries.PagedList;
import joist.domain.orm.queries.Select;
import joist.domain.orm.queries.SelectItem;

public class ParentQueries extends ParentQueriesCodegen {

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
    return Select.from(p).join(c.parent.on(p)).list();
  }

  public List<Parent> findChildrenStartingWith(String name) {
    ParentAlias p = new ParentAlias("p");
    ChildAlias c = new ChildAlias("c");

    Select<Parent> q = Select.from(p);
    q.join(c.parent.on(p));
    q.where(c.name.like(name + "%"));
    return q.list();
  }

  public List<ParentDto> findNumberOfChildren() {
    ParentAlias p = new ParentAlias("p");
    ChildAlias c = new ChildAlias("c");

    Select<Parent> q = Select.from(p);
    q.select(//
      p.name.as("name"),
      new SelectItem("COUNT(" + c.id.getQualifiedName() + ")", "numberOfChildren"));
    q.join(c.parent.leftOn(p));
    q.groupBy(p.name);
    return q.list(ParentDto.class);
  }

  public static class ParentDto {
    public String name;
    public long numberOfChildren;
  }
}
