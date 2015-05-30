package features.domain.queries;

import features.domain.UserTypesAFoo;
import joist.domain.AbstractQueries;

abstract class UserTypesAFooQueriesCodegen extends AbstractQueries<UserTypesAFoo> {

  public UserTypesAFooQueriesCodegen() {
    super(UserTypesAFoo.class);
  }

  public void delete(UserTypesAFoo instance) {
    super.delete(instance);
  }

}
