package features.migrations;

import static joist.migrations.MigrationKeywords.bool;
import static joist.migrations.MigrationKeywords.createTable;
import static joist.migrations.MigrationKeywords.integer;
import static joist.migrations.MigrationKeywords.primaryKey;
import static joist.migrations.MigrationKeywords.varchar;
import joist.migrations.AbstractMigration;

public class m0001 extends AbstractMigration {

  public m0001() {
    super("Table with primitives.");
  }

  public void apply() {
    createTable("primitives", primaryKey("id"), bool("flag"), varchar("name"), integer("version"));
  }

}
