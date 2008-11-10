package features.updates;

import static org.exigencecorp.domainobjects.updater.Keywords.createJoinTable;
import static org.exigencecorp.domainobjects.updater.Keywords.createTable;
import static org.exigencecorp.domainobjects.updater.Keywords.integer;
import static org.exigencecorp.domainobjects.updater.Keywords.primaryKey;
import static org.exigencecorp.domainobjects.updater.Keywords.varchar;

import org.exigencecorp.domainobjects.updater.Update;

public class Update0007 extends Update {

    public Update0007() {
        super("Many to many.");
    }

    public void apply() {
        createTable("many_to_many_a_foo",//
            primaryKey("id"),
            varchar("name"),
            integer("version"));

        createTable("many_to_many_a_bar",//
            primaryKey("id"),
            varchar("name"),
            integer("version"));

        createJoinTable("many_to_many_a_foo_to_bar", "many_to_many_a_foo", "many_to_many_a_bar");
    }

}
