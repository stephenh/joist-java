package org.exigencecorp.updater.columns;


public class CharColumn extends AbstractColumn {

    private Integer length;

    public CharColumn(String name, Integer length, Nullable isNull) {
        super(name, isNull);
        this.length = length;
    }

    public String toSql() {
        if (this.length != null) {
            return this.getName() + " char(" + this.length + "),";
        }
        return this.getName() + " char,";
    }

}
