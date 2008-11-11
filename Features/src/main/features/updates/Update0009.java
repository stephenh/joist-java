package features.updates;

import static org.exigencecorp.domainobjects.updater.Keywords.createTable;
import static org.exigencecorp.domainobjects.updater.Keywords.integer;
import static org.exigencecorp.domainobjects.updater.Keywords.primaryKey;
import static org.exigencecorp.domainobjects.updater.Keywords.varchar;

import org.exigencecorp.domainobjects.updater.Update;

public class Update0009 extends Update {

    public Update0009() {
        super("ValidationA.");
    }

    public void apply() {
        createTable("validation_a_foo", primaryKey("id"), varchar("name"), integer("version"));
    }

}
