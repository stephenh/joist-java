package features.domain.queries;

import joist.domain.orm.queries.Select;

import com.domainlanguage.time.CalendarDate;

import features.domain.UserTypesAFoo;
import features.domain.UserTypesAFooAlias;

public class UserTypesAFooQueries extends UserTypesAFooQueriesCodegen {

    public UserTypesAFoo findByCreated(CalendarDate on) {
        UserTypesAFooAlias utaf = new UserTypesAFooAlias("utaf");
        return Select.from(utaf).where(utaf.created.equals(on)).unique();
    }

}
