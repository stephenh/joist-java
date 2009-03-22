package joist.domain.migrations.columns;

import joist.util.StringBuilderr;
import joist.util.Wrap;

public abstract class AbstractColumn<T extends AbstractColumn<T>> implements Column {

    private final String name;
    private final String dataType;
    private String tableName;
    private boolean nullable = false;
    private boolean unique = false;

    protected AbstractColumn(String name, String dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    public String getNameInQuotes() {
        return Wrap.quotes(this.name);
    }

    public T nullable() {
        this.nullable = true;
        return (T) this;
    }

    public T unique() {
        this.unique = true;
        return (T) this;
    }

    public String getName() {
        return this.name;
    }

    public String getQuotedName() {
        return Wrap.quotes(this.name);
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String toSql() {
        return this.getQuotedName() + " " + this.getDataType();
    }

    public void preInjectCommands(StringBuilderr sb) {
    }

    public void postInjectCommands(StringBuilderr sb) {
        if (!this.isNullable()) {
            sb.line("ALTER TABLE \"{}\" ALTER COLUMN \"{}\" SET NOT NULL;", this.tableName, this.name);
        }
        if (this.isUnique()) {
            String constraintName = this.getTableName() + "_" + this.getName() + "_key";
            sb.line("ALTER TABLE \"{}\" ADD CONSTRAINT \"{}\" UNIQUE (\"{}\");", this.getTableName(), constraintName, this.getName());
        }
    }

    private boolean isNullable() {
        return this.nullable;
    }

    private boolean isUnique() {
        return this.unique;
    }

    public String getDataType() {
        return this.dataType;
    }

}
