package features.migrations;

import static joist.migrations.MigrationKeywords.createTable;
import static joist.migrations.MigrationKeywords.foreignKey;
import static joist.migrations.MigrationKeywords.integer;
import static joist.migrations.MigrationKeywords.primaryKey;
import static joist.migrations.MigrationKeywords.varchar;
import joist.migrations.AbstractMigration;

public class m0010 extends AbstractMigration {

  public m0010() {
    super("ParentC with two non-default foreign key names.");
  }

  public void apply() {
    createTable("parent_c_foo", primaryKey("id"), varchar("name"), integer("version"));
    createTable("parent_c_bar",//
      primaryKey("id"),
      varchar("name"),
      foreignKey("first_parent_id", "parent_c_foo"),
      foreignKey("second_parent_id", "parent_c_foo"),
      integer("version"));
  }

}
