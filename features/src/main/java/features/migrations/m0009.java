package features.migrations;

import static joist.migrations.MigrationKeywords.createTable;
import static joist.migrations.MigrationKeywords.integer;
import static joist.migrations.MigrationKeywords.primaryKey;
import static joist.migrations.MigrationKeywords.varchar;
import joist.migrations.AbstractMigration;

public class m0009 extends AbstractMigration {

  public m0009() {
    super("ValidationA.");
  }

  public void apply() {
    createTable("validation_a_foo", primaryKey("id"), varchar("name"), integer("version"));
  }

}
