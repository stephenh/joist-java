package features.updates;

import static org.exigencecorp.updater.Keywords.createTable;
import static org.exigencecorp.updater.Keywords.foreignKey;
import static org.exigencecorp.updater.Keywords.integer;
import static org.exigencecorp.updater.Keywords.primaryKey;
import static org.exigencecorp.updater.Keywords.varchar;

import org.exigencecorp.updater.Update;
import org.exigencecorp.updater.columns.ForeignKeyColumn.Owner;

public class Update0002 extends Update {

    public Update0002() {
        super("Parent/child.");
    }

    public void apply() {
        createTable("parent", primaryKey("id"), varchar("name"), integer("version"));
        createTable("child", primaryKey("id"), foreignKey("parent", Owner.IsThem), varchar("name"), integer("version"));
    }

}
