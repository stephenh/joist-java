package joist.domain.orm.queries.columns;

import java.util.Date;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.orm.queries.Alias;

import com.domainlanguage.time.TimePoint;

public class TimePointAliasColumn<T extends DomainObject> extends AliasColumn<T, TimePoint, Date> {

    public TimePointAliasColumn(Alias<T> alias, String name, Shim<T, TimePoint> shim) {
        super(alias, name, shim);
    }

    @Override
    public TimePoint toDomainValue(Date jdbcValue) {
        return jdbcValue == null ? null : TimePoint.from(jdbcValue);
    }

    @Override
    public Date toJdbcValue(TimePoint domainValue) {
        return domainValue == null ? null : new Date(domainValue.asJavaUtilDate().getTime());
    }

}
