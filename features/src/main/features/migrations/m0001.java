package features.migrations;

import static joist.domain.migrations.MigrationKeywords.bool;
import static joist.domain.migrations.MigrationKeywords.createTable;
import static joist.domain.migrations.MigrationKeywords.integer;
import static joist.domain.migrations.MigrationKeywords.primaryKey;
import static joist.domain.migrations.MigrationKeywords.varchar;
import joist.domain.migrations.AbstractMigration;

public class m0001 extends AbstractMigration {

    public m0001() {
        super("Table with primitives.");
    }

    public void apply() {
        createTable("primitives", primaryKey("id"), bool("flag"), varchar("name"), integer("version"));
    }

}
