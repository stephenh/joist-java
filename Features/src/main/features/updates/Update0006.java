package features.updates;

import static org.exigencecorp.updater.Keywords.createTable;
import static org.exigencecorp.updater.Keywords.createTableSubclass;
import static org.exigencecorp.updater.Keywords.foreignKey;
import static org.exigencecorp.updater.Keywords.integer;
import static org.exigencecorp.updater.Keywords.primaryKey;
import static org.exigencecorp.updater.Keywords.varchar;

import org.exigencecorp.updater.Update;
import org.exigencecorp.updater.columns.ForeignKeyColumn.Owner;

public class Update0006 extends Update {

    public Update0006() {
        super("Inheritance with multiple levels.");
    }

    public void apply() {
        createTable("inheritance_b_root",//
            primaryKey("id"),
            varchar("name"),
            integer("version"));

        createTable("inheritance_b_root_child",//
            primaryKey("id"),
            foreignKey("inheritance_b_root", Owner.IsThem),
            varchar("name"),
            integer("version"));

        createTableSubclass("inheritance_b_root",// 
            "inheritance_b_middle",
            primaryKey("id"),
            varchar("middle_name"));

        createTableSubclass("inheritance_b_middle",//
            "inheritance_b_bottom",
            primaryKey("id"),
            varchar("bottom_name"));
    }

}
