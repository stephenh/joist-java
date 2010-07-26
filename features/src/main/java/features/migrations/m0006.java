package features.migrations;

import static joist.migrations.MigrationKeywords.createTable;
import static joist.migrations.MigrationKeywords.createTableSubclass;
import static joist.migrations.MigrationKeywords.foreignKey;
import static joist.migrations.MigrationKeywords.integer;
import static joist.migrations.MigrationKeywords.primaryKey;
import static joist.migrations.MigrationKeywords.varchar;
import joist.migrations.AbstractMigration;

public class m0006 extends AbstractMigration {

    public m0006() {
        super("Inheritance with multiple levels.");
    }

    public void apply() {
        createTable("inheritance_b_root", primaryKey("id"), varchar("name"), integer("version"));
        createTable("inheritance_b_root_child", primaryKey("id"), foreignKey("inheritance_b_root"), varchar("name"), integer("version"));
        createTableSubclass("inheritance_b_root", "inheritance_b_middle", primaryKey("id"), varchar("middle_name"));
        createTableSubclass("inheritance_b_middle", "inheritance_b_bottom", primaryKey("id"), varchar("bottom_name"));
    }

}
