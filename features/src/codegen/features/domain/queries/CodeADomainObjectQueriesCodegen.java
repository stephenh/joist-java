package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.CodeADomainObject;

public abstract class CodeADomainObjectQueriesCodegen extends AbstractQueries<CodeADomainObject> {

  public CodeADomainObjectQueriesCodegen() {
    super(CodeADomainObject.class);
  }

  public void delete(CodeADomainObject instance) {
    super.delete(instance);
  }

}
