package features.updates;

import static org.exigencecorp.domainobjects.updater.Keywords.createTable;
import static org.exigencecorp.domainobjects.updater.Keywords.foreignKey;
import static org.exigencecorp.domainobjects.updater.Keywords.integer;
import static org.exigencecorp.domainobjects.updater.Keywords.primaryKey;
import static org.exigencecorp.domainobjects.updater.Keywords.varchar;

import org.exigencecorp.domainobjects.updater.Update;

public class Update0010 extends Update {

    public Update0010() {
        super("ParentC.");
    }

    public void apply() {
        createTable("parent_c_foo", primaryKey("id"), varchar("name"), integer("version"));
        createTable("parent_c_bar",//
            primaryKey("id"),
            varchar("name"),
            foreignKey("first_parent_id", "parent_c_foo"),
            foreignKey("second_parent_id", "parent_c_foo"),
            integer("version"));
    }

}
