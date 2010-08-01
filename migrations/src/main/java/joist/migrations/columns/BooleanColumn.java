package joist.migrations.columns;

import joist.migrations.MigrationKeywords;

public class BooleanColumn extends AbstractColumn<BooleanColumn> {

    private Boolean defaultValue = null;

    public BooleanColumn(String name) {
        super(name, MigrationKeywords.db.isMySQL() ? "bit" : "boolean");
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
        String sql = this.getQuotedName() + " " + (MigrationKeywords.db.isMySQL() ? "bit" : "boolean");
        if (this.defaultValue != null) {
            String defaultTrue = MigrationKeywords.db.isMySQL() ? "1" : "true";
            String defaultFalse = MigrationKeywords.db.isMySQL() ? "0" : "false";
            sql += " DEFAULT " + (this.defaultValue ? defaultTrue : defaultFalse);
        }
        return sql;
    }

}
