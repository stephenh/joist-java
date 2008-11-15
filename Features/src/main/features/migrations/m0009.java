package features.migrations;

import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.createTable;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.integer;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.primaryKey;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.varchar;

import org.exigencecorp.domainobjects.migrations.AbstractMigration;

public class m0009 extends AbstractMigration {

    public m0009() {
        super("ValidationA.");
    }

    public void apply() {
        createTable("validation_a_foo", primaryKey("id"), varchar("name"), integer("version"));
    }

}
