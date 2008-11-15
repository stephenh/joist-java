package org.exigencecorp.domainobjects.migrations.columns;

public class DatetimeColumn extends AbstractColumn<DatetimeColumn> {

    public DatetimeColumn(String name) {
        super(name);
    }

    public String toSql() {
        return this.getName() + " timestamp,";
    }

}
