package features.domain.queries;

import java.util.List;

import joist.domain.orm.queries.Select;

import com.domainlanguage.time.CalendarDate;
import com.domainlanguage.time.CalendarInterval;

import features.domain.UserTypesAFoo;
import features.domain.UserTypesAFooAlias;

public class UserTypesAFooQueries extends UserTypesAFooQueriesCodegen {

  public UserTypesAFoo findByCreated(CalendarDate on) {
    UserTypesAFooAlias utaf = new UserTypesAFooAlias("utaf");
    return Select.from(utaf).where(utaf.created.eq(on)).unique();
  }

  public List<UserTypesAFoo> findByCreatedWithin(CalendarInterval interval) {
    UserTypesAFooAlias utaf = new UserTypesAFooAlias("utaf");
    return Select.from(utaf).where(utaf.created.within(interval)).list();
  }

}
