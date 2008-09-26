package features.updates;

import static org.exigencecorp.updater.Keywords.createTable;
import static org.exigencecorp.updater.Keywords.createTableSubclass;
import static org.exigencecorp.updater.Keywords.integer;
import static org.exigencecorp.updater.Keywords.primaryKey;
import static org.exigencecorp.updater.Keywords.varchar;

import org.exigencecorp.updater.Update;

public class Update0003 extends Update {

    public Update0003() {
        super("Inheritance.");
    }

    public void apply() {
        createTable("inheritance_a_base", primaryKey("id"), varchar("name"), integer("version"));
        createTableSubclass("inheritance_a_base", "inheritance_a_sub_one", primaryKey("id"), varchar("one"));
        createTableSubclass("inheritance_a_base", "inheritance_a_sub_two", primaryKey("id"), varchar("two"));
    }

}
