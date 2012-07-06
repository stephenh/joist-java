package joist.migrations.columns;

import static org.junit.Assert.assertEquals;
import joist.domain.orm.Db;
import joist.migrations.MigrationKeywords;
import joist.util.Join;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ForeignKeyColumnTest {

  @Before
  public void setupDb() {
    MigrationKeywords.db = Db.MYSQL;
  }

  @Before
  public void dummyNamer() {
    ForeignKeyColumn.constraintNamer = new StubConstraintNamer();
  }

  @After
  public void restoreInt() {
    PrimaryKeyColumn.keyColumnType = "int";
  }

  @Test
  public void unsignedColumnType() {
    PrimaryKeyColumn.keyColumnType = "int unsigned";
    ForeignKeyColumn fk = new ForeignKeyColumn("parent");
    fk.setTableName("child");
    assertEquals("\"parent_id\" int unsigned", fk.toSql());
    assertEquals(Join.commaSpace(
      "ALTER TABLE \"child\" MODIFY \"parent_id\" int unsigned NOT NULL ;",
      "ALTER TABLE \"child\" ADD CONSTRAINT c_0_isneither_fk FOREIGN KEY (\"parent_id\") REFERENCES \"parent\" (\"id\")  ;"), Join.commaSpace(fk
      .postInjectCommands()));
  }

}
