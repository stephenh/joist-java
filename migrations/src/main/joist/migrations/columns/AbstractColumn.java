package joist.migrations.columns;

import java.util.ArrayList;
import java.util.List;

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
        return Wrap.backquotes(this.name);
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

    public List<String> preInjectCommands() {
        return new ArrayList<String>();
    }

    public List<String> postInjectCommands() {
        List<String> sqls = new ArrayList<String>();
        if (!this.isNullable()) {
            sqls.add(Interpolate.string("ALTER TABLE `{}` MODIFY `{}` {} NOT NULL;", this.tableName, this.name, this.getDataType()));
        } else {
            sqls.add(Interpolate.string("ALTER TABLE `{}` MODIFY `{}` {} NULL;", this.tableName, this.name, this.getDataType()));
        }
        if (this.isUnique()) {
            String constraintName = this.getTableName() + "_" + this.getName() + "_key";
            sqls.add(Interpolate.string("ALTER TABLE {} ADD CONSTRAINT {} UNIQUE ({});", this.getTableName(), constraintName, this.getName()));
        }
        return sqls;
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
