package features.migrations;

import static joist.migrations.MigrationKeywords.addNotNull;
import static joist.migrations.MigrationKeywords.dropColumn;
import static joist.migrations.MigrationKeywords.dropNotNull;
import static joist.migrations.MigrationKeywords.dropUniqueConstraint;
import joist.migrations.AbstractMigration;

public class m0020 extends AbstractMigration {

  public m0020() {
    super("dropNotNull/addNotNull part 2.");
  }

  public void apply() {
    dropNotNull("values_a", "i");
    dropNotNull("values_a", "a");
    addNotNull("values_a", "j");
    addNotNull("values_a", "b");
    dropUniqueConstraint("values_a", "name");
    dropColumn("values_a", "primitives_id");
    dropColumn("values_a", "description");
  }

}
