package org.exigencecorp.domainobjects.migrations.columns;

import org.exigencecorp.util.StringBuilderr;

public abstract class AbstractColumn<T extends AbstractColumn<T>> implements Column {

    private final String name;
    private String tableName;
    private Nullable isNull = Nullable.No;
    private IsUnique isUnique;

    protected AbstractColumn(String name) {
        this.name = name;
    }

    public T nullable() {
        this.isNull = Nullable.Yes;
        return (T) this;
    }

    public T unique() {
        this.isUnique = IsUnique.Yes;
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
        return this.isNull == Nullable.Yes;
    }

    private boolean isUnique() {
        return this.isUnique == IsUnique.Yes;
    }

}
