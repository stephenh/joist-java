package features.migrations;

import static joist.migrations.MigrationKeywords.createTable;
import static joist.migrations.MigrationKeywords.foreignKey;
import static joist.migrations.MigrationKeywords.integer;
import static joist.migrations.MigrationKeywords.primaryKey;
import static joist.migrations.MigrationKeywords.varchar;
import joist.migrations.AbstractMigration;

public class m0011 extends AbstractMigration {

    public m0011() {
        super("ManyToManyB with non-default foreign key column names.");
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
