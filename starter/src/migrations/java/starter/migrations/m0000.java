package starter.migrations;

import static joist.migrations.MigrationKeywords.*;
import joist.migrations.AbstractMigration;

public class m0000 extends AbstractMigration {

  public m0000() {
    super("Create schema_version infrastructure tables.");
  }

  public void apply() {
    createTable("schema_version", integer("update_lock"), integer("version"));
    execute("INSERT INTO schema_version (update_lock, version) values (1, -1)");

    createTable(
      "history_entry",
      primaryKey("id"),
      varchar("type"),
      varchar("root_table_name"),
      integer("primary_key"),
      varchar("property_name").length(255).nullable(),
      varchar("old_value").length(255).nullable(),
      varchar("new_value").length(255).nullable(),
      varchar("updater").nullable(),
      datetime("update_time"),
      integer("version"));
  }

}
