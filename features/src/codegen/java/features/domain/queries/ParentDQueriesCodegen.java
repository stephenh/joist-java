package features.domain.queries;

import features.domain.ParentD;
import features.domain.ParentDChildAAlias;
import features.domain.ParentDToChildCAlias;
import java.util.List;
import joist.domain.AbstractQueries;
import joist.domain.orm.queries.Select;

abstract class ParentDQueriesCodegen extends AbstractQueries<ParentD> {

  public ParentDQueriesCodegen() {
    super(ParentD.class);
  }

  public void delete(ParentD instance) {
    super.delete(instance);
  }

  public List<Long> findParentDChildAsIds(ParentD parentD) {
    ParentDChildAAlias pdca0 = new ParentDChildAAlias();
    return Select.from(pdca0).where(pdca0.parentD.eq(parentD)).listIds();
  }

  public List<Long> findParentDToChildCsIds(ParentD parentD) {
    ParentDToChildCAlias pdtcc0 = new ParentDToChildCAlias();
    return Select.from(pdtcc0).where(pdtcc0.parentD.eq(parentD)).listIds();
  }

}
