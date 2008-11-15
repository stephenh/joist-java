package features.migrations;

import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.createTable;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.foreignKey;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.integer;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.primaryKey;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.varchar;

import org.exigencecorp.domainobjects.migrations.AbstractMigration;

public class m0002 extends AbstractMigration {

    public m0002() {
        super("Parent/child.");
    }

    public void apply() {
        createTable("parent", primaryKey("id"), varchar("name"), integer("version"));
        createTable("child", primaryKey("id"), foreignKey("parent"), varchar("name"), integer("version"));
    }

}
