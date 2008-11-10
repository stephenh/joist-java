package features.updates;

import static org.exigencecorp.domainobjects.updater.Keywords.createTable;
import static org.exigencecorp.domainobjects.updater.Keywords.date;
import static org.exigencecorp.domainobjects.updater.Keywords.integer;
import static org.exigencecorp.domainobjects.updater.Keywords.primaryKey;
import static org.exigencecorp.domainobjects.updater.Keywords.varchar;

import org.exigencecorp.domainobjects.updater.Update;

public class Update0008 extends Update {

    public Update0008() {
        super("User types with timeandmoney.");
    }

    public void apply() {
        createTable("user_types_a_foo", primaryKey("id"), varchar("name"), date("created"), integer("version"));
    }

}
