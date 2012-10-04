package features.domain;

import org.junit.Assert;
import org.junit.Test;

import features.domain.builders.Builders;

public class DisconnectedTest extends AbstractFeaturesTest {

  @Test
  public void testForeignKeyListHolder() {
    Parent p = Builders.aParent().defaults().get();
    this.commitAndReOpen();
    try {
      // p is disconnected now (UoW is open, but p is not in it)
      p.getChilds().size();
      Assert.fail();
    } catch (IllegalStateException ise) {
      Assert.assertEquals("Instance has been disconnected from the UoW: Parent[1]", ise.getMessage());
    }
  }

  @Test
  public void testForeignKeyHolder() {
    Child c = Builders.aChild().defaults().get();
    this.commitAndReOpen();
    c = this.reload(c);
    this.commitAndReOpen();
    try {
      // c is disconnected now (UoW is open, but c is not in it)
      c.getParent();
      Assert.fail();
    } catch (IllegalStateException ise) {
      Assert.assertEquals("Instance has been disconnected from the UoW", ise.getMessage());
    }
  }

}
