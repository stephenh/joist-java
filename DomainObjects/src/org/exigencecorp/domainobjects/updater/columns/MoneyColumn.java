package org.exigencecorp.domainobjects.updater.columns;

public class MoneyColumn extends AbstractColumn<MoneyColumn> {

    public MoneyColumn(String name) {
        super(name);
    }

    public String toSql() {
        return this.getName() + " int,";
    }

}
