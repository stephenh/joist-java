package org.exigencecorp.updater.columns;


public class BooleanColumn extends AbstractColumn {

    private Boolean defaultValue;

    public BooleanColumn(String name) {
        this(name, Nullable.No);
    }

    public BooleanColumn(String name, Nullable isNull) {
        super(name, isNull);
    }

    public BooleanColumn(String name, Nullable isNull, boolean defaultValue) {
        super(name, isNull);
        this.defaultValue = defaultValue;
    }

    public String toSql() {
        String sql = this.getName() + " boolean";

        if (this.isNull == Nullable.No) {
            sql += " NOT NULL";
        }

        if (this.defaultValue != null) {
            sql += " DEFAULT " + this.defaultValue.toString().toUpperCase() + ",";
        } else if (this.isNull == Nullable.Yes) {
            sql += " DEFAULT NULL,";
        } else {
            sql += ",";
        }

        return sql;
    }

}
