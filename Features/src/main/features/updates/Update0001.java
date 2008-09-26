package features.updates;

import static org.exigencecorp.updater.Keywords.bool;
import static org.exigencecorp.updater.Keywords.createTable;
import static org.exigencecorp.updater.Keywords.integer;
import static org.exigencecorp.updater.Keywords.primaryKey;
import static org.exigencecorp.updater.Keywords.varchar;

import org.exigencecorp.updater.Update;

public class Update0001 extends Update {

    public Update0001() {
        super("Table with primitives.");
    }

    public void apply() {
        createTable("primitives", primaryKey("id"), bool("flag"), varchar("name"), integer("version"));
    }

}
