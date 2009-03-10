package joist.domain.migrations.columns;

import org.exigencecorp.util.StringBuilderr;

public abstract class AbstractColumn<T extends AbstractColumn<T>> implements Column {

    private final String name;
    private String tableName;
    private boolean nullable = false;
    private boolean unique = false;

    protected AbstractColumn(String name) {
        this.name = name;
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

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String toSql() {
        throw new IllegalStateException("The concrete column class must override us");
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

}
