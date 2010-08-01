package features.migrations;

import static joist.migrations.MigrationKeywords.bigint;
import static joist.migrations.MigrationKeywords.createTable;
import static joist.migrations.MigrationKeywords.datetime;
import static joist.migrations.MigrationKeywords.integer;
import static joist.migrations.MigrationKeywords.primaryKey;
import static joist.migrations.MigrationKeywords.varchar;
import joist.migrations.AbstractMigration;

public class m0014 extends AbstractMigration {

    public m0014() {
        super("PrimitivesC with amount.");
    }

    public void apply() {
        createTable("primitives_c",//
            primaryKey("id"),
            varchar("name"),
            bigint("dollar_amount"),
            datetime("timestamp"),
            integer("version"));
    }

}
