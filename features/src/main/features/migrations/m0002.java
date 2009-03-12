package features.migrations;

import static joist.domain.migrations.MigrationKeywords.createTable;
import static joist.domain.migrations.MigrationKeywords.foreignKey;
import static joist.domain.migrations.MigrationKeywords.integer;
import static joist.domain.migrations.MigrationKeywords.primaryKey;
import static joist.domain.migrations.MigrationKeywords.varchar;
import joist.domain.migrations.AbstractMigration;

public class m0002 extends AbstractMigration {

    public m0002() {
        super("Parent/child.");
    }

    public void apply() {
        createTable("parent", primaryKey("id"), varchar("name"), integer("version"));
        createTable("child", primaryKey("id"), foreignKey("parent"), varchar("name"), integer("version"));
    }

}
