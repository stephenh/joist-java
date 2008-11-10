package org.exigencecorp.domainobjects.updater.columns;

public class BigIntColumn extends AbstractColumn {

    public BigIntColumn(String name) {
        this(name, Nullable.No);
    }

    public BigIntColumn(String name, Nullable isNull) {
        super(name, isNull);
    }

    public String toSql() {
        return this.getName() + " BIGINT,";
    }

}
