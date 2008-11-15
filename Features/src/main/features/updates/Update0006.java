package features.updates;

import static org.exigencecorp.domainobjects.updater.Keywords.createTable;
import static org.exigencecorp.domainobjects.updater.Keywords.createTableSubclass;
import static org.exigencecorp.domainobjects.updater.Keywords.foreignKey;
import static org.exigencecorp.domainobjects.updater.Keywords.integer;
import static org.exigencecorp.domainobjects.updater.Keywords.primaryKey;
import static org.exigencecorp.domainobjects.updater.Keywords.varchar;

import org.exigencecorp.domainobjects.updater.Update;

public class Update0006 extends Update {

    public Update0006() {
        super("Inheritance with multiple levels.");
    }

    public void apply() {
        createTable("inheritance_b_root", primaryKey("id"), varchar("name"), integer("version"));
        createTable("inheritance_b_root_child", primaryKey("id"), foreignKey("inheritance_b_root"), varchar("name"), integer("version"));
        createTableSubclass("inheritance_b_root", "inheritance_b_middle", primaryKey("id"), varchar("middle_name"));
        createTableSubclass("inheritance_b_middle", "inheritance_b_bottom", primaryKey("id"), varchar("bottom_name"));
    }

}
