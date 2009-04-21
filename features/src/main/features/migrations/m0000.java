package features.migrations;

import static joist.domain.migrations.MigrationKeywords.createTable;
import static joist.domain.migrations.MigrationKeywords.execute;
import static joist.domain.migrations.MigrationKeywords.integer;
import static joist.domain.migrations.MigrationKeywords.varchar;
import joist.domain.migrations.AbstractMigration;

public class m0000 extends AbstractMigration {

    public m0000() {
        super("Create schema_version.");
    }

    public void apply() {
        createTable("schema_version", integer("update_lock"), integer("version"));
        execute("INSERT INTO schema_version (update_lock, version) values (1, -1)");
        execute("CREATE SEQUENCE constraint_name_seq");
        createTable("code_id", varchar("table_name").unique(), integer("next_id"));
    }

}
