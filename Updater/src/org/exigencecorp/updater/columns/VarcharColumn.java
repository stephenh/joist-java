package org.exigencecorp.updater.columns;

import org.exigencecorp.util.StringBuilderr;

public class VarcharColumn extends AbstractColumn {

    private int length;
    private IsUnique isUnique;

    public VarcharColumn(String name) {
        this(name, 100, Nullable.No, IsUnique.No);
    }

    public VarcharColumn(String name, int length) {
        this(name, length, Nullable.No, IsUnique.No);
    }

    public VarcharColumn(String name, IsUnique isUnique) {
        this(name, 100, Nullable.No, isUnique);
    }

    public VarcharColumn(String name, Nullable isNull) {
        this(name, 100, isNull, IsUnique.No);
    }

    public VarcharColumn(String name, int length, Nullable isNull, IsUnique isUnique) {
        super(name, isNull);
        this.length = length;
        this.isUnique = isUnique;
    }

    public String toSql() {
        return "\"" + this.getName() + "\" varchar(" + this.length + "),";
    }

    public void postInjectCommands(StringBuilderr sb) {
        super.postInjectCommands(sb);
        if (this.isUnique == IsUnique.Yes) {
            String constraintName = this.getTableName() + "_" + this.getName() + "_key";
            sb.line("ALTER TABLE '{}' ADD CONSTRAINT {} UNIQUE ({});", this.getTableName(), constraintName, this.getName());
        }
    }

}
