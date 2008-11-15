package org.exigencecorp.domainobjects.updater.columns;

public class SmallIntColumn extends AbstractColumn<SmallIntColumn> {

    public SmallIntColumn(String name) {
        super(name);
    }

    public String toSql() {
        return this.getName() + " smallint,";
    }

}
