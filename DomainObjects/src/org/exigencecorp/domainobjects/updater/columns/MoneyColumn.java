package org.exigencecorp.domainobjects.updater.columns;

public class MoneyColumn extends AbstractColumn {

    public MoneyColumn(String name) {
        this(name, Nullable.No);
    }

    public MoneyColumn(String name, Nullable isNull) {
        super(name, isNull);
    }

    public String toSql() {
        return this.getName() + " int,";
    }

}
