package features.migrations;

import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.createTable;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.foreignKey;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.integer;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.primaryKey;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.varchar;

import org.exigencecorp.domainobjects.migrations.AbstractMigration;

public class m0010 extends AbstractMigration {

    public m0010() {
        super("ParentC.");
    }

    public void apply() {
        createTable("parent_c_foo", primaryKey("id"), varchar("name"), integer("version"));
        createTable("parent_c_bar",//
            primaryKey("id"),
            varchar("name"),
            foreignKey("first_parent_id", "parent_c_foo"),
            foreignKey("second_parent_id", "parent_c_foo"),
            integer("version"));
    }

}
