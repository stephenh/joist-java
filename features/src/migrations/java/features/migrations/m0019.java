package features.migrations;

import static joist.migrations.MigrationKeywords.createEntityTable;
import static joist.migrations.MigrationKeywords.foreignKey;
import static joist.migrations.MigrationKeywords.integer;
import static joist.migrations.MigrationKeywords.varchar;
import joist.migrations.AbstractMigration;

public class m0019 extends AbstractMigration {

  public m0019() {
    super("dropNotNull/addNotNull part 1.");
  }

  public void apply() {
    createEntityTable("values_a",//
      varchar("name").unique(), // unique dropped in m0020
      varchar("description").unique(), // column dropped in m0020
      foreignKey("primitives"), // column dropped in m0020
      varchar("a").defaultValue("a"),
      varchar("b").defaultValue("b").nullable(),
      integer("i").defaultValue(1),
      integer("j").nullable().defaultValue(2));
  }

}
