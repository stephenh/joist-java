package org.exigencecorp.domainobjects.updater.columns;

public class IntColumn extends AbstractColumn {

    public IntColumn(String name) {
        this(name, Nullable.No);
    }

    public IntColumn(String name, Nullable isNull) {
        super(name, isNull);
    }

    public String toSql() {
        return "\"" + this.getName() + "\" int,";
    }

}
