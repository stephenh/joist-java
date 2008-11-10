package features.domain.orm;

import java.sql.Date;
import java.util.TimeZone;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.converters.Converter;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.columns.AliasColumn;

import com.domainlanguage.time.CalendarDate;
import com.domainlanguage.time.TimePoint;

public class CalendarDateAliasColumn<T extends DomainObject> extends AliasColumn<T, CalendarDate, Date> {

    private static final Converter<CalendarDate, Date> converterInstance = new Converter<CalendarDate, Date>() {
        public Date toJdbc(CalendarDate value) {
            return new Date(value.startAsTimePoint(TimeZone.getDefault()).asJavaUtilDate().getTime());
        }

        public CalendarDate toDomain(Date value) {
            return CalendarDate.from(TimePoint.from(value), TimeZone.getDefault());
        }
    };

    public CalendarDateAliasColumn(Alias<T> alias, String name, Shim<T, CalendarDate> shim) {
        super(alias, name, shim);
        this.converter = converterInstance;
    }
}
