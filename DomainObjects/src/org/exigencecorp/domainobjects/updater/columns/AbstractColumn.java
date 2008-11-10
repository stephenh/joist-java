package org.exigencecorp.domainobjects.updater.columns;

import java.sql.SQLException;

import org.exigencecorp.util.StringBuilderr;

public abstract class AbstractColumn implements Column {

    private String name;
    private String tableName;
    protected Nullable isNull;

    protected AbstractColumn(String name, Nullable isNull) {
        this.name = name;
        this.isNull = isNull;
    }

    public String getName() {
        return this.name;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String toSql() {
        throw new IllegalStateException("The concrete column class must override us");
    }

    public String toSqlWithoutTrailingComma() throws SQLException {
        String s = this.toSql();
        s = s.substring(0, s.length() - 1);
        return s;
    }

    public void preInjectCommands(StringBuilderr sb) {
    }

    public void postInjectCommands(StringBuilderr sb) {
        if (this.isNull == Nullable.No) {
            sb.line("ALTER TABLE \"{}\" ALTER COLUMN \"{}\" SET NOT NULL;", this.tableName, this.name);
        }
    }

}
