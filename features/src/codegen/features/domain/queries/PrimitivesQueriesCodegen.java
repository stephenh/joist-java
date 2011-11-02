package features.domain.queries;

import joist.domain.AbstractQueries;
import joist.domain.orm.queries.Select;
import features.domain.Primitives;
import features.domain.PrimitivesAlias;

public abstract class PrimitivesQueriesCodegen extends AbstractQueries<Primitives> {

  public PrimitivesQueriesCodegen() {
    super(Primitives.class);
  }

  public void delete(Primitives instance) {
    super.delete(instance);
  }

  public Primitives findByName(String name) {
    PrimitivesAlias p0 = new PrimitivesAlias("p0");
    Select<Primitives> q = Select.from(p0);
    q.where(p0.name.eq(name));
    return q.unique();
  }

}
