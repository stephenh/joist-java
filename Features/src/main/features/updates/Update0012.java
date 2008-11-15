package features.updates;

import static org.exigencecorp.domainobjects.updater.Keywords.bigint;
import static org.exigencecorp.domainobjects.updater.Keywords.bool;
import static org.exigencecorp.domainobjects.updater.Keywords.createTable;
import static org.exigencecorp.domainobjects.updater.Keywords.integer;
import static org.exigencecorp.domainobjects.updater.Keywords.primaryKey;
import static org.exigencecorp.domainobjects.updater.Keywords.smallint;

import org.exigencecorp.domainobjects.updater.Update;

public class Update0012 extends Update {

    public Update0012() {
        super("PrimitivesB with null/not-null.");
    }

    public void apply() {
        createTable("primitives_b",//
            primaryKey("id"),
            bool("bool1").nullable(),
            bool("bool2"),
            integer("int1").nullable(),
            integer("int2"),
            smallint("small1").nullable(),
            smallint("small2"),
            bigint("big1").nullable(),
            bigint("big2"),
            integer("version"));
    }

}
