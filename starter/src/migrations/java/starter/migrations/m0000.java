package starter.migrations;

import static joist.migrations.MigrationKeywords.createTable;
import static joist.migrations.MigrationKeywords.execute;
import static joist.migrations.MigrationKeywords.integer;
import joist.migrations.AbstractMigration;

public class m0000 extends AbstractMigration {

  public m0000() {
    super("Create schema_version infrastructure tables.");
  }

  public void apply() {
    createTable("schema_version", integer("update_lock"), integer("version"));
    execute("INSERT INTO schema_version (update_lock, version) values (1, -1)");
  }

}
