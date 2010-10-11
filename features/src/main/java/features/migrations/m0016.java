package features.migrations;

import static joist.migrations.MigrationKeywords.createTable;
import static joist.migrations.MigrationKeywords.foreignKey;
import static joist.migrations.MigrationKeywords.integer;
import static joist.migrations.MigrationKeywords.primaryKey;
import static joist.migrations.MigrationKeywords.varchar;
import joist.migrations.AbstractMigration;

public class m0016 extends AbstractMigration {

  public m0016() {
    super("ParentD.");
  }

  public void apply() {
    createTable("parent_d", primaryKey("id"), varchar("name"), integer("version"));
    createTable("parent_d_child_a", primaryKey("id"), foreignKey("parent_d"), varchar("name"), integer("version"));
    createTable("parent_d_child_b", primaryKey("id"), foreignKey("parent_d"), varchar("name"), integer("version"));
    // many to many
    createTable("parent_d_child_c", primaryKey("id"), varchar("name"), integer("version"));
    createTable("parent_d_to_child_c", primaryKey("id"), foreignKey("parent_d"), foreignKey("parent_d_child_c"), integer("version"));
  }

}
