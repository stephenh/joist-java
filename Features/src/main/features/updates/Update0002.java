package features.updates;

import static org.exigencecorp.domainobjects.updater.Keywords.createTable;
import static org.exigencecorp.domainobjects.updater.Keywords.foreignKey;
import static org.exigencecorp.domainobjects.updater.Keywords.integer;
import static org.exigencecorp.domainobjects.updater.Keywords.primaryKey;
import static org.exigencecorp.domainobjects.updater.Keywords.theyOwnMe;
import static org.exigencecorp.domainobjects.updater.Keywords.varchar;

import org.exigencecorp.domainobjects.updater.Update;

public class Update0002 extends Update {

    public Update0002() {
        super("Parent/child.");
    }

    public void apply() {
        createTable("parent", primaryKey("id"), varchar("name"), integer("version"));
        createTable("child", primaryKey("id"), foreignKey("parent", theyOwnMe), varchar("name"), integer("version"));
    }

}
