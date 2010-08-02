package features.migrations;

import static joist.migrations.MigrationKeywords.createTable;
import static joist.migrations.MigrationKeywords.createTableSubclass;
import static joist.migrations.MigrationKeywords.integer;
import static joist.migrations.MigrationKeywords.primaryKey;
import static joist.migrations.MigrationKeywords.varchar;
import joist.migrations.AbstractMigration;

public class m0015 extends AbstractMigration {

  public m0015() {
    super("InheritanceC.");
  }

  public void apply() {
    createTable("inheritance_c", primaryKey("id"), varchar("name"), integer("version"));
    createTableSubclass("inheritance_c", "inheritance_c_foo1", primaryKey("id"), varchar("foo"));
    createTableSubclass("inheritance_c", "inheritance_c_foo2", primaryKey("id"), varchar("foo"));
  }

}
