package features.migrations;

import static joist.migrations.MigrationKeywords.bigint;
import static joist.migrations.MigrationKeywords.bool;
import static joist.migrations.MigrationKeywords.createTable;
import static joist.migrations.MigrationKeywords.integer;
import static joist.migrations.MigrationKeywords.primaryKey;
import static joist.migrations.MigrationKeywords.smallint;
import joist.migrations.AbstractMigration;

public class m0012 extends AbstractMigration {

    public m0012() {
        super("PrimitivesB with null/not-null.");
    }

    public void apply() {
        createTable("primitives_b",//
            primaryKey("id"),
            bool("bool1").nullable(),
            bool("bool2"),
            integer("int1").nullable(),
            integer("int2"),
            smallint("small1").nullable(),
            smallint("small2"),
            bigint("big1").nullable(),
            bigint("big2"),
            integer("version"));
    }

}
