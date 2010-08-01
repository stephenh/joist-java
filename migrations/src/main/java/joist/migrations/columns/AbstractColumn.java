package joist.migrations.columns;

import java.util.ArrayList;
import java.util.List;

import joist.migrations.MigrationKeywords;
import joist.util.Interpolate;
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

    @SuppressWarnings("unchecked")
    public T nullable() {
        this.nullable = true;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
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

    public List<String> postInjectCommands() {
        List<String> sqls = new ArrayList<String>();
        if (!this.isNullable()) {
            this.addNotNull(sqls);
        } else {
            this.addNull(sqls);
        }
        if (this.isUnique()) {
            String constraintName = this.getTableName() + "_" + this.getName() + "_key";
            sqls.add(Interpolate.string(
                "ALTER TABLE {} ADD CONSTRAINT {} UNIQUE ({});",
                Wrap.quotes(this.getTableName()),
                Wrap.quotes(constraintName),
                Wrap.quotes(this.getName())));
        }
        return sqls;
    }

    private void addNotNull(List<String> sqls) {
        if (MigrationKeywords.db.isMySQL()) {
            sqls.add(Interpolate.string(
                "ALTER TABLE {} MODIFY {} {} NOT NULL;",
                Wrap.quotes(this.tableName),
                Wrap.quotes(this.name),
                this.getDataType()));
        } else if (MigrationKeywords.db.isPg()) {
            sqls.add(Interpolate.string(//
                "ALTER TABLE {} ALTER COLUMN {} SET NOT NULL;",
                Wrap.quotes(this.tableName),
                Wrap.quotes(this.name)));
        } else {
            throw new IllegalStateException("Unhandled db " + MigrationKeywords.db);
        }
    }

    private void addNull(List<String> sqls) {
        // ...why does MySQL need this again?
        if (MigrationKeywords.db.isMySQL()) {
            sqls.add(Interpolate.string("ALTER TABLE {} MODIFY {} {} NULL;",//
                Wrap.quotes(this.tableName),
                Wrap.quotes(this.name),
                this.getDataType()));
        }
    }

    protected boolean isNullable() {
        return this.nullable;
    }

    protected boolean isUnique() {
        return this.unique;
    }

    public String getDataType() {
        return this.dataType;
    }

}
