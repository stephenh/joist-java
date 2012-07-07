package features.migrations;

import static joist.migrations.MigrationKeywords.createEntityTable;
import static joist.migrations.MigrationKeywords.createSubclassTable;
import static joist.migrations.MigrationKeywords.varchar;
import joist.migrations.AbstractMigration;

public class m0015 extends AbstractMigration {

  public m0015() {
    super("InheritanceC.");
  }

  public void apply() {
    createEntityTable("inheritance_c", varchar("name"));
    createSubclassTable("inheritance_c", "inheritance_c_foo1", varchar("foo"));
    createSubclassTable("inheritance_c", "inheritance_c_foo2", varchar("foo"));
  }

}
