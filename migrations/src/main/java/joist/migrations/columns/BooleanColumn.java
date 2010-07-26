package joist.migrations.columns;

public class BooleanColumn extends AbstractColumn<BooleanColumn> {

    private Boolean defaultValue = null;

    public BooleanColumn(String name) {
        super(name, "bit");
    }

    public BooleanColumn defaultTrue() {
        this.defaultValue = Boolean.TRUE;
        return this;
    }

    public BooleanColumn defaultFalse() {
        this.defaultValue = Boolean.FALSE;
        return this;
    }

    public String toSql() {
        String sql = this.getQuotedName() + " bit";
        if (this.defaultValue != null) {
            sql += " DEFAULT " + ((this.defaultValue) ? "1" : "0");
        }
        return sql;
    }

}
