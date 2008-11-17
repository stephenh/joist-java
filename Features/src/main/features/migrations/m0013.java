package features.migrations;

import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.createTable;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.foreignKey;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.integer;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.primaryKey;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.varchar;

import org.exigencecorp.domainobjects.migrations.AbstractMigration;

public class m0013 extends AbstractMigration {

    public m0013() {
        super("OneToOneA.");
    }

    public void apply() {
        createTable("one_to_one_a_foo", primaryKey("id"), varchar("name"), integer("version"));
        createTable("one_to_one_a_bar", primaryKey("id"), varchar("name"), foreignKey("one_to_one_a_foo").unique(), integer("version"));
    }

}
