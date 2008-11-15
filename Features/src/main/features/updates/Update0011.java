package features.updates;

import static org.exigencecorp.domainobjects.updater.Keywords.createTable;
import static org.exigencecorp.domainobjects.updater.Keywords.foreignKey;
import static org.exigencecorp.domainobjects.updater.Keywords.integer;
import static org.exigencecorp.domainobjects.updater.Keywords.primaryKey;
import static org.exigencecorp.domainobjects.updater.Keywords.varchar;

import org.exigencecorp.domainobjects.updater.Update;

public class Update0011 extends Update {

    public Update0011() {
        super("ManyToManyB.");
    }

    public void apply() {
        createTable("many_to_many_b_foo",//
            primaryKey("id"),
            varchar("name"),
            integer("version"));

        createTable("many_to_many_b_bar",//
            primaryKey("id"),
            varchar("name"),
            integer("version"));

        createTable("many_to_many_b_foo_to_bar",//
            primaryKey("id"),
            foreignKey("blue_id", "many_to_many_b_foo").ownerIsNeither(),
            foreignKey("green_id", "many_to_many_b_bar").ownerIsNeither(),
            integer("version"));
    }

}
