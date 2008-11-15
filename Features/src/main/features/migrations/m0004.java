package features.migrations;

import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.addCode;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.createCodeTable;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.createTable;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.foreignKey;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.integer;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.primaryKey;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.varchar;

import org.exigencecorp.domainobjects.migrations.AbstractMigration;

public class m0004 extends AbstractMigration {

    public m0004() {
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
