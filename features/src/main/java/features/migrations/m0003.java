package features.migrations;

import static joist.migrations.MigrationKeywords.createEntityTable;
import static joist.migrations.MigrationKeywords.createSubclassTable;
import static joist.migrations.MigrationKeywords.foreignKey;
import static joist.migrations.MigrationKeywords.varchar;
import joist.migrations.AbstractMigration;

public class m0003 extends AbstractMigration {

  public m0003() {
    super("Inheritance.");
  }

  public void apply() {
    createEntityTable("inheritance_a_owner", varchar("name"));
    createEntityTable("inheritance_a_thing", varchar("name"));

    createEntityTable("inheritance_a_base", foreignKey("inheritance_a_owner").nullable(), varchar("name"));
    createSubclassTable("inheritance_a_base", "inheritance_a_sub_one", foreignKey("inheritance_a_thing").nullable(), varchar("one"));
    createSubclassTable("inheritance_a_base", "inheritance_a_sub_two", foreignKey("inheritance_a_thing").nullable(), varchar("two"));
  }
}
