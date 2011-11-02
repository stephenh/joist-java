package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.ValidationAFoo;

public abstract class ValidationAFooQueriesCodegen extends AbstractQueries<ValidationAFoo> {

  public ValidationAFooQueriesCodegen() {
    super(ValidationAFoo.class);
  }

  public void delete(ValidationAFoo instance) {
    super.delete(instance);
  }

}
