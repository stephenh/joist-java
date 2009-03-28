package joist.domain.migrations.columns;

public class VarcharColumn extends AbstractColumn<VarcharColumn> {

    private int length;

    public VarcharColumn(String name) {
        super(name, "varchar");
        this.length = 100;
    }

    public VarcharColumn length(int length) {
        this.length = length;
        return this;
    }

    public String toSql() {
        return this.getQuotedName() + " varchar(" + this.length + "),";
    }

}
