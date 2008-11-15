package org.exigencecorp.domainobjects.migrations.columns;

public class IntColumn extends AbstractColumn<IntColumn> {

    public IntColumn(String name) {
        super(name);
    }

    public String toSql() {
        return "\"" + this.getName() + "\" int,";
    }

}
