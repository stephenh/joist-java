package features.domain.queries;

import features.domain.Primitives;
import features.domain.PrimitivesAlias;
import joist.domain.AbstractQueries;
import joist.domain.orm.queries.Select;

public abstract class PrimitivesQueriesCodegen extends AbstractQueries<Primitives> {

  public PrimitivesQueriesCodegen() {
    super(Primitives.class);
  }

  public void delete(Primitives instance) {
    super.delete(instance);
  }

  public Primitives findByName(String name) {
    PrimitivesAlias p0 = new PrimitivesAlias("p0");
    return Select.from(p0).where(p0.name.eq(name)).unique();
  }

}
