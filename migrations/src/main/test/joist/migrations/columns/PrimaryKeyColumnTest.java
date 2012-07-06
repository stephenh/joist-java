package joist.migrations.columns;

import static org.junit.Assert.assertEquals;
import joist.domain.orm.Db;
import joist.migrations.MigrationKeywords;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PrimaryKeyColumnTest {

  @Before
  public void setupDb() {
    MigrationKeywords.db = Db.MYSQL;
  }

  @After
  public void restoreInt() {
    PrimaryKeyColumn.keyColumnType = "int";
  }

  @Test
  public void unsignedColumnType() {
    PrimaryKeyColumn.keyColumnType = "int unsigned";
    PrimaryKeyColumn p = new PrimaryKeyColumn("id");
    assertEquals("\"id\" int unsigned AUTO_INCREMENT PRIMARY KEY", p.toSql());
  }

}
