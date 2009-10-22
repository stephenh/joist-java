package features.migrations;

import static joist.domain.migrations.MigrationKeywords.bigint;
import static joist.domain.migrations.MigrationKeywords.createTable;
import static joist.domain.migrations.MigrationKeywords.integer;
import static joist.domain.migrations.MigrationKeywords.primaryKey;
import static joist.domain.migrations.MigrationKeywords.varchar;
import joist.domain.migrations.AbstractMigration;

public class m0014 extends AbstractMigration {

    public m0014() {
        super("PrimitivesC with amount.");
    }

    public void apply() {
        createTable("primitives_c", primaryKey("id"), varchar("name"), bigint("dollar_amount"), integer("version"));
    }

}
