package features.migrations;

import javax.sql.DataSource;

import joist.domain.orm.Db;
import joist.jdbc.Jdbc;
import joist.migrations.SchemaCheck;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import features.Registry;
import features.domain.AbstractFeaturesTest;
import features.domain.SchemaHash;

public class SchemaCheckTest extends AbstractFeaturesTest {

  private DataSource ds;
  private Db db;
  private String schemaName;

  @Before
  @Override
  public void setUp() {
    super.setUp();
    this.db = Registry.getRepository().getDb();
    this.ds = Registry.getDataSource();
    this.schemaName = this.db.isPg() ? "public" : "features";
  }

  @Test
  public void testJavaCodeIsntInDatabase() {
    Jdbc.update(this.ds, "delete from code_a_color where id = 2");
    try {
      new SchemaCheck(this.db, this.schemaName, "features.domain", this.ds).checkCodesMatch();
      Assert.fail();
    } catch (RuntimeException re) {
      Assert.assertEquals("Code code_a_color 2-GREEN is not in the database", re.getMessage());
    } finally {
      Jdbc.update(this.ds, "insert into code_a_color (id, code, name, version) values (2, 'GREEN', 'Green', 0)");
    }
  }

  @Test
  public void testDatabaseCodeIsntInJava() {
    // Add "Other" to db"
    Jdbc.update(this.ds, "insert into code_a_color (id, code, name, version) values (3, 'O', 'Other', 0)");
    try {
      new SchemaCheck(this.db, this.schemaName, "features.domain", this.ds).checkCodesMatch();
      Assert.fail();
    } catch (RuntimeException re) {
      Assert.assertEquals("Database code code_a_color 3 is not in the codebase", re.getMessage());
    } finally {
      Jdbc.update(this.ds, "delete from code_a_color where id = 3");
    }
  }

  @Test
  public void testJavaCodeIdCollision() {
    // Change "F" to "O"
    Jdbc.update(this.ds, "update code_a_color set code = 'O' where code = 'GREEN'");
    try {
      new SchemaCheck(this.db, this.schemaName, "features.domain", this.ds).checkCodesMatch();
      Assert.fail();
    } catch (RuntimeException re) {
      Assert.assertEquals("Code code_a_color 2-GREEN's id is taken by a different code", re.getMessage());
    } finally {
      Jdbc.update(this.ds, "update code_a_color set code = 'GREEN' where code = 'O'");
    }
  }

  @Test
  public void testExtraStructurePasses() {
    new SchemaCheck(this.db, this.schemaName, "features.domain", this.ds).checkStructureMatch(SchemaHash.hashCode);
  }

  public void brokenTestExtraColumn() {
    Jdbc.update(this.ds, "alter table code_a_color add column foo int");
    try {
      new SchemaCheck(this.db, this.schemaName, "features.domain", this.ds).checkStructureMatch(SchemaHash.hashCode);
      Assert.fail();
    } catch (RuntimeException re) {
      Assert.assertEquals("Database hash did not match the codebase's generated hash", re.getMessage());
    } finally {
      Jdbc.update(this.ds, "alter table code_a_color drop column foo");
    }
  }

}
