package features.updates;

import static org.exigencecorp.updater.Keywords.createTable;
import static org.exigencecorp.updater.Keywords.foreignKey;
import static org.exigencecorp.updater.Keywords.integer;
import static org.exigencecorp.updater.Keywords.primaryKey;
import static org.exigencecorp.updater.Keywords.varchar;

import org.exigencecorp.updater.Update;
import org.exigencecorp.updater.columns.ForeignKeyColumn.Owner;

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
            foreignKey("parent_b_parent", Owner.IsThem),
            varchar("name"),
            integer("version"));

        createTable("parent_b_child_bar",//
            primaryKey("id"),
            foreignKey("parent_b_parent", Owner.IsThem),
            varchar("name"),
            integer("version"));
    }

}
