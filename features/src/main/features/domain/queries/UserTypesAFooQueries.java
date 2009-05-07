package features.domain.queries;

import joist.domain.AbstractQueries;
import joist.domain.orm.queries.Select;

import com.domainlanguage.time.CalendarDate;

import features.domain.UserTypesAFoo;
import features.domain.UserTypesAFooAlias;

public class UserTypesAFooQueries extends AbstractQueries<UserTypesAFoo> {

    public UserTypesAFooQueries() {
        super(UserTypesAFoo.class);
    }

    public UserTypesAFoo findByCreated(CalendarDate on) {
        UserTypesAFooAlias utaf = new UserTypesAFooAlias("utaf");
        return Select.from(utaf).where(utaf.created.equals(on)).unique();
    }

}
