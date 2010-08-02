package features.migrations;

import static joist.migrations.MigrationKeywords.createTable;
import static joist.migrations.MigrationKeywords.execute;
import static joist.migrations.MigrationKeywords.integer;
import static joist.migrations.MigrationKeywords.varchar;
import joist.migrations.AbstractMigration;

public class m0000 extends AbstractMigration {

  public m0000() {
    super("Create schema_version.");
  }

  public void apply() {
    createTable("schema_version", integer("update_lock"), integer("version"));
    execute("INSERT INTO schema_version (update_lock, version) values (1, -1)");
    createTable("code_id", varchar("table_name").unique(), integer("next_id"));
  }

}
