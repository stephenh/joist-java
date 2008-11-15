package org.exigencecorp.domainobjects.updater.columns;

public class BigIntColumn extends AbstractColumn<BigIntColumn> {

    public BigIntColumn(String name) {
        super(name);
    }

    public String toSql() {
        return this.getName() + " bigint,";
    }

}
