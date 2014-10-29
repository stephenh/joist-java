package features.migrations;

import static joist.migrations.MigrationKeywords.createTable;
import static joist.migrations.MigrationKeywords.datetime;
import static joist.migrations.MigrationKeywords.integer;
import static joist.migrations.MigrationKeywords.primaryKey;
import static joist.migrations.MigrationKeywords.varchar;
import joist.migrations.AbstractMigration;

public class m0017 extends AbstractMigration {

  public m0017() {
    super("History table.");
  }

  public void apply() {
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
