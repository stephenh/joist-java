package org.exigencecorp.domainobjects.migrations.columns;

public class BigIntColumn extends AbstractColumn<BigIntColumn> {

    public BigIntColumn(String name) {
        super(name);
    }

    public String toSql() {
        return this.getName() + " bigint,";
    }

}
