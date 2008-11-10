package org.exigencecorp.domainobjects.updater.columns;

public class SmallIntColumn extends AbstractColumn {

    public SmallIntColumn(String name, Nullable isNull) {
        super(name, isNull);
    }

    public String toSql() {
        return this.getName() + " SMALLINT,";
    }

}
