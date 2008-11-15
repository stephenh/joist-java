package features.migrations;

import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.createTable;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.foreignKey;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.integer;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.primaryKey;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.varchar;

import org.exigencecorp.domainobjects.migrations.AbstractMigration;

public class m0011 extends AbstractMigration {

    public m0011() {
        super("ManyToManyB.");
    }

    public void apply() {
        createTable("many_to_many_b_foo",//
            primaryKey("id"),
            varchar("name"),
            integer("version"));

        createTable("many_to_many_b_bar",//
            primaryKey("id"),
            varchar("name"),
            integer("version"));

        createTable("many_to_many_b_foo_to_bar",//
            primaryKey("id"),
            foreignKey("blue_id", "many_to_many_b_foo").ownerIsNeither(),
            foreignKey("green_id", "many_to_many_b_bar").ownerIsNeither(),
            integer("version"));
    }

}
