package features.migrations;

import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.bigint;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.bool;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.createTable;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.integer;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.primaryKey;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.smallint;

import org.exigencecorp.domainobjects.migrations.AbstractMigration;

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
