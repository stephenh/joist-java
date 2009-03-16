package joist.domain.migrations.columns;

import joist.util.StringBuilderr;

public class VarcharColumn extends AbstractColumn<VarcharColumn> {

    private int length;

    public VarcharColumn(String name) {
        super(name);
        this.length = 100;
    }

    public VarcharColumn length(int length) {
        this.length = length;
        return this;
    }

    public String toSql() {
        return "\"" + this.getName() + "\" varchar(" + this.length + "),";
    }

    public void postInjectCommands(StringBuilderr sb) {
        super.postInjectCommands(sb);
    }

}
