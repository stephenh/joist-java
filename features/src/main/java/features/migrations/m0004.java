package features.migrations;

import static joist.migrations.MigrationKeywords.addCode;
import static joist.migrations.MigrationKeywords.createCodeTable;
import static joist.migrations.MigrationKeywords.createTable;
import static joist.migrations.MigrationKeywords.foreignKey;
import static joist.migrations.MigrationKeywords.integer;
import static joist.migrations.MigrationKeywords.primaryKey;
import static joist.migrations.MigrationKeywords.varchar;
import joist.migrations.AbstractMigration;

public class m0004 extends AbstractMigration {

  public m0004() {
    super("Codes.");
  }

  public void apply() {
    createCodeTable("code_a_size");
    addCode("code_a_size", "ONE", "One");
    addCode("code_a_size", "TWO", "Two");

    createCodeTable("code_a_color");
    addCode("code_a_color", "BLUE", "Blue");
    addCode("code_a_color", "GREEN", "Green");

    createTable(
      "code_a_domain_object",
      primaryKey("id"),
      foreignKey("code_a_size").ownerIsNeither(),
      foreignKey("code_a_color").ownerIsNeither(),
      varchar("name"),
      integer("version"));
  }

}
