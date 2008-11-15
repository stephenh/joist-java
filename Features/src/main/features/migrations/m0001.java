package features.migrations;

import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.bool;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.createTable;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.integer;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.primaryKey;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.varchar;

import org.exigencecorp.domainobjects.migrations.AbstractMigration;

public class m0001 extends AbstractMigration {

    public m0001() {
        super("Table with primitives.");
    }

    public void apply() {
        createTable("primitives", primaryKey("id"), bool("flag"), varchar("name"), integer("version"));
    }

}
