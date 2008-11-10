package features.updates;

import static org.exigencecorp.domainobjects.updater.Keywords.bool;
import static org.exigencecorp.domainobjects.updater.Keywords.createTable;
import static org.exigencecorp.domainobjects.updater.Keywords.integer;
import static org.exigencecorp.domainobjects.updater.Keywords.primaryKey;
import static org.exigencecorp.domainobjects.updater.Keywords.varchar;

import org.exigencecorp.domainobjects.updater.Update;

public class Update0001 extends Update {

    public Update0001() {
        super("Table with primitives.");
    }

    public void apply() {
        createTable("primitives", primaryKey("id"), bool("flag"), varchar("name"), integer("version"));
    }

}
