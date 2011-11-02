package features.domain.queries;

import joist.domain.AbstractQueries;
import features.domain.UserTypesAFoo;

public abstract class UserTypesAFooQueriesCodegen extends AbstractQueries<UserTypesAFoo> {

  public UserTypesAFooQueriesCodegen() {
    super(UserTypesAFoo.class);
  }

  public void delete(UserTypesAFoo instance) {
    super.delete(instance);
  }

}
