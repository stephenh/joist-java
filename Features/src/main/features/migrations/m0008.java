package features.migrations;

import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.createTable;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.date;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.integer;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.primaryKey;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.varchar;

import org.exigencecorp.domainobjects.migrations.AbstractMigration;

public class m0008 extends AbstractMigration {

    public m0008() {
        super("User types with timeandmoney.");
    }

    public void apply() {
        createTable("user_types_a_foo", primaryKey("id"), varchar("name"), date("created"), integer("version"));
    }

}
