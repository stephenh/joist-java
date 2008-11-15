package features.updates;

import static org.exigencecorp.domainobjects.updater.Keywords.addCode;
import static org.exigencecorp.domainobjects.updater.Keywords.createCodeTable;
import static org.exigencecorp.domainobjects.updater.Keywords.createTable;
import static org.exigencecorp.domainobjects.updater.Keywords.foreignKey;
import static org.exigencecorp.domainobjects.updater.Keywords.integer;
import static org.exigencecorp.domainobjects.updater.Keywords.primaryKey;
import static org.exigencecorp.domainobjects.updater.Keywords.varchar;

import org.exigencecorp.domainobjects.updater.Update;

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
            foreignKey("code_a_size").ownerIsNeither(),
            foreignKey("code_a_color").ownerIsNeither(),
            varchar("name"),
            integer("version"));
    }

}
