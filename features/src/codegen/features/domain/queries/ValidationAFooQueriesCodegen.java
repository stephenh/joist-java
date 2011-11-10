package features.domain.queries;

import features.domain.ValidationAFoo;
import joist.domain.AbstractQueries;

public abstract class ValidationAFooQueriesCodegen extends AbstractQueries<ValidationAFoo> {

  public ValidationAFooQueriesCodegen() {
    super(ValidationAFoo.class);
  }

  public void delete(ValidationAFoo instance) {
    super.delete(instance);
  }

}
