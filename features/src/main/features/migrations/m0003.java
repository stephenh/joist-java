package features.migrations;

import static joist.domain.migrations.MigrationKeywords.createTable;
import static joist.domain.migrations.MigrationKeywords.createTableSubclass;
import static joist.domain.migrations.MigrationKeywords.integer;
import static joist.domain.migrations.MigrationKeywords.primaryKey;
import static joist.domain.migrations.MigrationKeywords.varchar;
import joist.domain.migrations.AbstractMigration;

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
