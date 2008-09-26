package org.exigencecorp.updater.columns;

/** Store YYYY-MM-DD. */
public class DateColumn extends AbstractColumn {

    public DateColumn(String name) {
        this(name, Nullable.No);
    }

    public DateColumn(String name, Nullable isNull) {
        super(name, isNull);
    }

    public String toSql() {
        return "\"" + this.getName() + "\" date,";
    }

}
