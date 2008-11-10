package features.updates;

import static org.exigencecorp.domainobjects.updater.Keywords.createTable;
import static org.exigencecorp.domainobjects.updater.Keywords.foreignKey;
import static org.exigencecorp.domainobjects.updater.Keywords.integer;
import static org.exigencecorp.domainobjects.updater.Keywords.primaryKey;
import static org.exigencecorp.domainobjects.updater.Keywords.theyOwnMe;
import static org.exigencecorp.domainobjects.updater.Keywords.varchar;

import org.exigencecorp.domainobjects.updater.Update;

public class Update0005 extends Update {

    public Update0005() {
        super("Parent with multiple children.");
    }

    public void apply() {
        createTable("parent_b_parent",//
            primaryKey("id"),
            varchar("name"),
            integer("version"));

        createTable("parent_b_child_foo",//
            primaryKey("id"),
            foreignKey("parent_b_parent", theyOwnMe),
            varchar("name"),
            integer("version"));

        createTable("parent_b_child_bar",//
            primaryKey("id"),
            foreignKey("parent_b_parent", theyOwnMe),
            varchar("name"),
            integer("version"));
    }

}
