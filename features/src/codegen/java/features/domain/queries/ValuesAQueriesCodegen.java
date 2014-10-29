package features.domain.queries;

import features.domain.ValuesA;
import features.domain.ValuesAAlias;
import joist.domain.AbstractQueries;
import joist.domain.orm.queries.Select;

abstract class ValuesAQueriesCodegen extends AbstractQueries<ValuesA> {

  public ValuesAQueriesCodegen() {
    super(ValuesA.class);
  }

  public void delete(ValuesA instance) {
    super.delete(instance);
  }

  public ValuesA findByName(String name) {
    ValuesAAlias va0 = new ValuesAAlias("va0");
    return Select.from(va0).where(va0.name.eq(name)).unique();
  }

}
