package features.domain.queries;

import java.util.List;

import joist.domain.orm.queries.Select;
import features.domain.ParentBChildFoo;
import features.domain.ParentBChildFooAlias;
import features.domain.ParentBParentAlias;

public class ParentBChildFooQueries extends ParentBChildFooQueriesCodegen {

  public List<ParentBChildFoo> findByParentName(String name) {
    ParentBParentAlias p = new ParentBParentAlias("p");
    ParentBChildFooAlias f = new ParentBChildFooAlias("f");

    Select<ParentBChildFoo> q = Select.from(f);
    q.join(p.on(f.parentBParent));
    q.where(p.name.eq(name));
    return q.list();
  }

}
