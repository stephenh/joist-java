package features.migrations;

import static joist.migrations.MigrationKeywords.addUniqueConstraint;
import joist.migrations.AbstractMigration;

public class m0021 extends AbstractMigration {

  public m0021() {
    super("dropNotNull/addNotNull part 3.");
  }

  public void apply() {
    addUniqueConstraint("values_a", "name");
  }

}
