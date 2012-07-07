package features.migrations;

import static joist.migrations.MigrationKeywords.createEntityTable;
import static joist.migrations.MigrationKeywords.createSubclassTable;
import static joist.migrations.MigrationKeywords.foreignKey;
import static joist.migrations.MigrationKeywords.varchar;
import joist.migrations.AbstractMigration;

public class m0006 extends AbstractMigration {

  public m0006() {
    super("Inheritance with multiple levels.");
  }

  public void apply() {
    createEntityTable("inheritance_b_root", varchar("name"));
    createEntityTable("inheritance_b_root_child", foreignKey("inheritance_b_root"), varchar("name"));
    createSubclassTable("inheritance_b_root", "inheritance_b_middle", varchar("middle_name"));
    createSubclassTable("inheritance_b_middle", "inheritance_b_bottom", varchar("bottom_name"));
  }

}
