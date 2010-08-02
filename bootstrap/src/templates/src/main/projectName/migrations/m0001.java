package @projectName@.migrations;

import static joist.domain.migrations.MigrationKeywords.*;
import joist.domain.migrations.AbstractMigration;

public class m0001 extends AbstractMigration {

    public m0001() {
        super("Create user.");
    }

    public void apply() {
        createTable("user",//
            primaryKey("id"),
            varchar("username").unique(),
            integer("version"));
    }

}
