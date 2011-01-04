package features.migrations;

import static joist.migrations.MigrationKeywords.createTable;
import static joist.migrations.MigrationKeywords.createTableSubclass;
import static joist.migrations.MigrationKeywords.foreignKey;
import static joist.migrations.MigrationKeywords.integer;
import static joist.migrations.MigrationKeywords.primaryKey;
import static joist.migrations.MigrationKeywords.varchar;
import joist.migrations.AbstractMigration;

public class m0003 extends AbstractMigration {

  public m0003() {
    super("Inheritance.");
  }

  public void apply() {
    createTable("inheritance_a_owner", primaryKey("id"), varchar("name"), integer("version"));
    createTable("inheritance_a_thing", primaryKey("id"), varchar("name"), integer("version"));

    createTable("inheritance_a_base", primaryKey("id"), foreignKey("inheritance_a_owner").nullable(), varchar("name"), integer("version"));
    createTableSubclass("inheritance_a_base", "inheritance_a_sub_one", primaryKey("id"), foreignKey("inheritance_a_thing").nullable(), varchar("one"));
    createTableSubclass("inheritance_a_base", "inheritance_a_sub_two", primaryKey("id"), foreignKey("inheritance_a_thing").nullable(), varchar("two"));
  }
}
