package features.migrations;

import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.createJoinTable;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.createTable;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.integer;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.primaryKey;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.varchar;

import org.exigencecorp.domainobjects.migrations.AbstractMigration;

public class m0007 extends AbstractMigration {

    public m0007() {
        super("Many to many.");
    }

    public void apply() {
        createTable("many_to_many_a_foo",//
            primaryKey("id"),
            varchar("name"),
            integer("version"));

        createTable("many_to_many_a_bar",//
            primaryKey("id"),
            varchar("name"),
            integer("version"));

        createJoinTable("many_to_many_a_foo_to_bar", "many_to_many_a_foo", "many_to_many_a_bar");
    }

}
