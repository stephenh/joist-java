package joist.domain.migrations.commands;

import joist.domain.migrations.columns.ForeignKeyColumn;
import joist.domain.migrations.columns.PrimaryKeyColumn;
import joist.domain.migrations.columns.VarcharColumn;
import joist.util.Join;
import junit.framework.Assert;
import junit.framework.TestCase;

public class CreateTableTest extends TestCase {

    public void testSequence() throws Exception {
        CreateTable t = new CreateTable("tpa", new PrimaryKeyColumn("id"), new VarcharColumn("name"));

        Assert.assertEquals(Join.linesWithTickToQuote(
            "CREATE SEQUENCE tpa_id_seq;",
            "CREATE TABLE 'tpa' (",
            "    id int DEFAULT nextval(''tpa_id_seq'') NOT NULL, PRIMARY KEY (id),",
            "    'name' varchar(100)",
            ");",
            "ALTER TABLE 'tpa' ALTER COLUMN 'id' SET NOT NULL;",
            "ALTER TABLE 'tpa' ALTER COLUMN 'name' SET NOT NULL;"), t.toSql());
    }

    public void testVarcharUnique() throws Exception {
        CreateTable t = new CreateTable("gender", new VarcharColumn("name").unique());

        Assert.assertEquals(Join.linesWithTickToQuote(
            "CREATE TABLE 'gender' (",
            "    'name' varchar(100)",
            ");",
            "ALTER TABLE 'gender' ALTER COLUMN 'name' SET NOT NULL;",
            "ALTER TABLE 'gender' ADD CONSTRAINT 'gender_name_key' UNIQUE ('name');"), t.toSql());
    }

    public void testForeignKey() throws Exception {
        CreateTable t = new CreateTable("demographic", new ForeignKeyColumn("gender").ownerIsNeither());
        Assert
            .assertEquals(
                Join
                    .linesWithTickToQuote(
                        "CREATE TABLE 'demographic' (",
                        "    \"gender_id\" int",
                        ");",
                        "ALTER TABLE 'demographic' ALTER COLUMN 'gender_id' SET NOT NULL;",
                        "ALTER TABLE 'demographic' ADD CONSTRAINT 'constraint_1_owner_isneither_fk' FOREIGN KEY ('gender_id') REFERENCES 'gender' ('id') DEFERRABLE;",
                        "CREATE INDEX 'demographic_gender_id_idx' ON 'demographic' USING btree (gender_id);"),
                t.toSql());
    }

}
