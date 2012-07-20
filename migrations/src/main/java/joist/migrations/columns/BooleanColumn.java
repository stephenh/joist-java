package joist.migrations.columns;

import joist.migrations.MigrationKeywords;

public class BooleanColumn extends AbstractColumn<BooleanColumn> {

  public BooleanColumn(String name) {
    super(name, MigrationKeywords.isMySQL() ? "bit" : "boolean");
  }

  public BooleanColumn defaultTrue() {
    return this.defaultValue(MigrationKeywords.isMySQL() ? "1" : "true");
  }

  public BooleanColumn defaultFalse() {
    return this.defaultValue(MigrationKeywords.isMySQL() ? "0" : "false");
  }

}
