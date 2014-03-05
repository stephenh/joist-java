package features.domain.queries;

import features.domain.ParentBParent;
import features.domain.ParentBParentAlias;
import joist.domain.AbstractQueries;
import joist.domain.orm.queries.Select;

abstract class ParentBParentQueriesCodegen extends AbstractQueries<ParentBParent> {

  public ParentBParentQueriesCodegen() {
    super(ParentBParent.class);
  }

  public void delete(ParentBParent instance) {
    super.delete(instance);
  }

  public ParentBParent findByName(String name) {
    ParentBParentAlias pbp0 = new ParentBParentAlias("pbp0");
    return Select.from(pbp0).where(pbp0.name.eq(name)).unique();
  }

}
