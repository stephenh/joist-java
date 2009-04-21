package features.migrations;

import static joist.domain.migrations.MigrationKeywords.createTable;
import static joist.domain.migrations.MigrationKeywords.integer;
import static joist.domain.migrations.MigrationKeywords.primaryKey;
import static joist.domain.migrations.MigrationKeywords.varchar;
import joist.domain.migrations.AbstractMigration;

public class m0009 extends AbstractMigration {

    public m0009() {
        super("ValidationA.");
    }

    public void apply() {
        createTable("validation_a_foo", primaryKey("id"), varchar("name"), integer("version"));
    }

}
