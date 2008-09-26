package features.updates;

import static org.exigencecorp.updater.Keywords.createTable;
import static org.exigencecorp.updater.Keywords.execute;
import static org.exigencecorp.updater.Keywords.integer;
import static org.exigencecorp.updater.Keywords.isUnique;
import static org.exigencecorp.updater.Keywords.varchar;

import org.exigencecorp.updater.Update;

public class Update0000 extends Update {

    public Update0000() {
        super("Create schema_version.");
    }

    public void apply() {
        createTable("schema_version", integer("update_lock"), integer("version"));
        execute("INSERT INTO schema_version (update_lock, version) values (1, -1)");
        execute("CREATE SEQUENCE constraint_name_seq");
        createTable("code_id", varchar("table_name", isUnique), integer("next_id"));
    }

}
