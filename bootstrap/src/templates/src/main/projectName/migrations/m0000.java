package @projectName@.migrations;

import static joist.domain.migrations.MigrationKeywords.*;
import joist.domain.migrations.AbstractMigration;

public class m0000 extends AbstractMigration {

    public m0000() {
        super("Create schema_version.");
    }

    public void apply() {
        createTable("schema_version", integer("update_lock"), integer("version"));
        execute("INSERT INTO schema_version (update_lock, version) values (1, -1)");
        createTable("code_id", varchar("table_name").unique(), integer("next_id"));
    }

}
