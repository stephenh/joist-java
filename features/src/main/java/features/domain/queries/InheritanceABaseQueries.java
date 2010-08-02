package features.domain.queries;

import java.util.List;

import joist.domain.orm.queries.Select;
import features.domain.InheritanceABase;
import features.domain.InheritanceABaseAlias;

public class InheritanceABaseQueries extends InheritanceABaseQueriesCodegen {

  public List<InheritanceABase> findAll() {
    InheritanceABaseAlias b = new InheritanceABaseAlias("b");
    return Select.from(b).orderBy(b.id.asc()).list();
  }

}
