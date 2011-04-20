package starter.migrations;

import static joist.migrations.MigrationKeywords.createCodeTable;
import static joist.migrations.MigrationKeywords.createTable;
import static joist.migrations.MigrationKeywords.foreignKey;
import static joist.migrations.MigrationKeywords.integer;
import static joist.migrations.MigrationKeywords.primaryKey;
import static joist.migrations.MigrationKeywords.varchar;

import java.sql.SQLException;

import joist.migrations.AbstractMigration;

public class m0001 extends AbstractMigration {

  public m0001() {
    super("First migration");
  }

  @Override
  public void apply() throws SQLException {
    createCodeTable("employee_type",//
      "FTE Full-time",
      "PTE Part-time");
    createTable("employee",//
      primaryKey("id"),
      foreignKey("employee_type"),
      varchar("name"),
      integer("version"));
  }

}
