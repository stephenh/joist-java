package starter.domain;

import static starter.domain.builders.Builders.aEmployee;
import joist.domain.FlushTestDatabase;
import joist.domain.orm.Db;
import joist.domain.orm.Repository;
import joist.domain.uow.UoW;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmployeeTest {

  @BeforeClass
  public static void beforeClass() {
    // change to Db.PG for postgres
    Repository.configure(Db.MYSQL, "starter");
  }

  @Before
  public void before() {
    FlushTestDatabase.execute();
    UoW.open(null);
  }

  @After
  public void after() {
    UoW.closeIfOpen();
  }

  @Test
  public void foo() {
    aEmployee().name("bob").employeeType(EmployeeType.FTE);
    UoW.commitAndReOpen();
  }

}
