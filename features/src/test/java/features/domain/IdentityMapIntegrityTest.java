package features.domain;

import joist.domain.uow.UoW;
import joist.jdbc.JdbcException;

import org.junit.Assert;
import org.junit.Test;

public class IdentityMapIntegrityTest extends AbstractFeaturesTest {

  @Test
  public void testInsertsGoIntoTheIdentityMap() {
    new Parent("p");
    this.flush();
    Assert.assertTrue(UoW.getIdentityMap().findOrNull(Parent.class, 1l) != null);
  }

  @Test
  public void testExplicitlySettingTheIdOfANewObject() {
    Parent p = new Parent("p");
    p.setId(10l);
    this.commitAndReOpen();

    p = Parent.queries.find(10);
    Assert.assertEquals("p", p.getName());
  }

  @Test
  public void testLoadIdThenAssignInUseIdIsCaughtByIdentityMap() {
    Parent p2 = new Parent("p2");
    this.commitAndReOpen();

    p2 = this.reload(p2);
    Parent pFake = new Parent("pFake");
    try {
      pFake.setId(1l);
      Assert.fail();
    } catch (RuntimeException re) {
      Assert.assertEquals("Domain object conflicts with an existing id Parent[1]", re.getMessage());
    }
  }

  @Test
  public void testAssignInUseIdThenLoadReturnsSameObjectSoIsNotCaughtUntilFlush() {
    Parent p2 = new Parent("p2");
    this.commitAndReOpen();

    // p2 = this.reload(p2);
    Parent pFake = new Parent("pFake");
    pFake.setId(1l);
    p2 = this.reload(p2);
    Assert.assertSame(pFake, p2);

    try {
      this.commitAndReOpen();
      Assert.fail();
    } catch (JdbcException re) {
      Assert.assertTrue(re.getCause().getMessage().toLowerCase().contains("duplicate"));
    }
  }

}
