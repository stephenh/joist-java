package org.exigencecorp.domainobjects.migrations.columns;

public class CharColumn extends AbstractColumn<CharColumn> {

    private Integer length;

    public CharColumn(String name, Integer length) {
        super(name);
        this.length = length;
    }

    public String toSql() {
        if (this.length != null) {
            return this.getName() + " char(" + this.length + "),";
        }
        return this.getName() + " char,";
    }

}
