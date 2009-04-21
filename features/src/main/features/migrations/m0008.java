package features.migrations;

import static joist.domain.migrations.MigrationKeywords.createTable;
import static joist.domain.migrations.MigrationKeywords.date;
import static joist.domain.migrations.MigrationKeywords.integer;
import static joist.domain.migrations.MigrationKeywords.primaryKey;
import static joist.domain.migrations.MigrationKeywords.varchar;
import joist.domain.migrations.AbstractMigration;

public class m0008 extends AbstractMigration {

    public m0008() {
        super("User types with timeandmoney.");
    }

    public void apply() {
        createTable("user_types_a_foo", primaryKey("id"), varchar("name"), date("created"), integer("version"));
    }

}
