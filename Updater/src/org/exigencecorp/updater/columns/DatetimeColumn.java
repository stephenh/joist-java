package org.exigencecorp.updater.columns;


public class DatetimeColumn extends AbstractColumn {

    public DatetimeColumn(String name) {
        this(name, Nullable.No);
    }

    public DatetimeColumn(String name, Nullable isNull) {
        super(name, isNull);
    }

    public String toSql() {
        return this.getName() + " timestamp,";
    }

}
