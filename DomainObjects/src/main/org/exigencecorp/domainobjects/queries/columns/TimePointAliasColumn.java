package org.exigencecorp.domainobjects.queries.columns;

import java.sql.Date;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.queries.Alias;

import com.domainlanguage.time.TimePoint;

public class TimePointAliasColumn<T extends DomainObject> extends AliasColumn<T, TimePoint, Date> {

    public TimePointAliasColumn(Alias<T> alias, String name, Shim<T, TimePoint> shim) {
        super(alias, name, shim);
    }

    @Override
    public TimePoint toDomainValue(Date jdbcValue) {
        return TimePoint.from(jdbcValue);
    }

    @Override
    public Date toJdbcValue(TimePoint domainValue) {
        return new Date(domainValue.asJavaUtilDate().getTime());
    }

}
