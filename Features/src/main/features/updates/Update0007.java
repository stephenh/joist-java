package features.updates;

import static org.exigencecorp.updater.Keywords.createJoinTable;
import static org.exigencecorp.updater.Keywords.createTable;
import static org.exigencecorp.updater.Keywords.integer;
import static org.exigencecorp.updater.Keywords.primaryKey;
import static org.exigencecorp.updater.Keywords.varchar;

import org.exigencecorp.updater.Update;

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
