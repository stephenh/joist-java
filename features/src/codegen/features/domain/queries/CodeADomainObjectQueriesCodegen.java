package features.domain.queries;

import features.domain.CodeADomainObject;
import joist.domain.AbstractQueries;

public abstract class CodeADomainObjectQueriesCodegen extends AbstractQueries<CodeADomainObject> {

    public CodeADomainObjectQueriesCodegen() {
        super(CodeADomainObject.class);
    }

    public void delete(CodeADomainObject instance) {
        super.delete(instance);
    }

}
