package features.migrations;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.exigencecorp.domainobjects.migrations.SchemaCheck;
import org.exigencecorp.jdbc.Jdbc;

import features.Registry;
import features.domain.AbstractFeaturesTest;

public class SchemaCheckTest extends AbstractFeaturesTest {

    private DataSource ds;

    public void setUp() throws Exception {
        super.setUp();
        this.ds = Registry.getDataSource();
    }

    public void testJavaCodeIsntInDatabase() {
        Jdbc.executeUpdate(this.ds, "delete from code_a_color where id = 2");
        try {
            new SchemaCheck("features.domain", this.ds).checkCodesMatch();
            Assert.fail();
        } catch (RuntimeException re) {
            Assert.assertEquals("Code code_a_color 2-GREEN is not in the database", re.getMessage());
        } finally {
            Jdbc.executeUpdate(this.ds, "insert into code_a_color (id, code, name, version) values (2, 'GREEN', 'Green', 0)");
        }
    }

    public void testDatabaseCodeIsntInJava() {
        // Add "Other" to db"
        Jdbc.executeUpdate(this.ds, "insert into code_a_color (id, code, name, version) values (3, 'O', 'Other', 0)");
        try {
            new SchemaCheck("features.domain", this.ds).checkCodesMatch();
            Assert.fail();
        } catch (RuntimeException re) {
            Assert.assertEquals("Database code code_a_color 3 is not in the codebase", re.getMessage());
        } finally {
            Jdbc.executeUpdate(this.ds, "delete from code_a_color where id = 3");
        }
    }

    public void testJavaCodeIdCollision() {
        // Change "F" to "O"
        Jdbc.executeUpdate(this.ds, "update code_a_color set code = 'O' where code = 'GREEN'");
        try {
            new SchemaCheck("features.domain", this.ds).checkCodesMatch();
            Assert.fail();
        } catch (RuntimeException re) {
            Assert.assertEquals("Code code_a_color 2-GREEN's id is taken by a different code", re.getMessage());
        } finally {
            Jdbc.executeUpdate(this.ds, "update code_a_color set code = 'GREEN' where code = 'O'");
        }
    }

    public void testSequenceValueTooLow() {
        Jdbc.executeUpdate(this.ds, "update code_id set next_id = 2 where table_name = 'code_a_color'");
        try {
            new SchemaCheck("features.domain", this.ds).checkCodesMatch();
            Assert.fail();
        } catch (RuntimeException re) {
            Assert.assertEquals("Code code_a_color has a max id of 2 but the last assigned was 1", re.getMessage());
        } finally {
            Jdbc.executeUpdate(this.ds, "update code_id set next_id = 3 where table_name = 'code_a_color'");
        }
    }

}
