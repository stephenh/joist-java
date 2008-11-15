package features.migrations;

import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.createTable;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.createTableSubclass;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.integer;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.primaryKey;
import static org.exigencecorp.domainobjects.migrations.MigrationKeywords.varchar;

import org.exigencecorp.domainobjects.migrations.AbstractMigration;

public class m0003 extends AbstractMigration {

    public m0003() {
        super("Inheritance.");
    }

    public void apply() {
        createTable("inheritance_a_base", primaryKey("id"), varchar("name"), integer("version"));
        createTableSubclass("inheritance_a_base", "inheritance_a_sub_one", primaryKey("id"), varchar("one"));
        createTableSubclass("inheritance_a_base", "inheritance_a_sub_two", primaryKey("id"), varchar("two"));
    }

}
