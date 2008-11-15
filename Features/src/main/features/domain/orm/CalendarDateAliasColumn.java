package features.domain.orm;

import java.sql.Date;
import java.util.TimeZone;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.columns.AliasColumn;

import com.domainlanguage.time.CalendarDate;
import com.domainlanguage.time.TimePoint;

public class CalendarDateAliasColumn<T extends DomainObject> extends AliasColumn<T, CalendarDate, Date> {

    public CalendarDateAliasColumn(Alias<T> alias, String name, Shim<T, CalendarDate> shim) {
        super(alias, name, shim);
    }

    @Override
    public CalendarDate toDomainValue(Date jdbcValue) {
        return CalendarDate.from(TimePoint.from(jdbcValue), TimeZone.getDefault());
    }

    @Override
    public Date toJdbcValue(CalendarDate domainValue) {
        return new Date(domainValue.startAsTimePoint(TimeZone.getDefault()).asJavaUtilDate().getTime());
    }

}
