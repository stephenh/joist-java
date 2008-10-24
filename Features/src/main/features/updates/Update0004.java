package features.updates;

import static org.exigencecorp.updater.Keywords.addCode;
import static org.exigencecorp.updater.Keywords.createCodeTable;
import static org.exigencecorp.updater.Keywords.createTable;
import static org.exigencecorp.updater.Keywords.foreignKey;
import static org.exigencecorp.updater.Keywords.integer;
import static org.exigencecorp.updater.Keywords.primaryKey;
import static org.exigencecorp.updater.Keywords.varchar;

import org.exigencecorp.updater.Update;
import org.exigencecorp.updater.columns.ForeignKeyColumn.Owner;

public class Update0004 extends Update {

    public Update0004() {
        super("Codes.");
    }

    public void apply() {
        createCodeTable("code_a_size");
        addCode("code_a_size", "ONE", "One");
        addCode("code_a_size", "TWO", "Two");

        createCodeTable("code_a_color");
        addCode("code_a_color", "BLUE", "Blue");
        addCode("code_a_color", "GREEN", "Green");

        createTable(
            "code_a_domain_object",
            primaryKey("id"),
            foreignKey("code_a_size", Owner.IsNeither),
            foreignKey("code_a_color", Owner.IsNeither),
            varchar("name"),
            integer("version"));
    }

}
