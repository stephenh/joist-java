package features.domain;

import joist.domain.exceptions.OpLockException;
import joist.jdbc.Jdbc;

import org.junit.Assert;
import org.junit.Test;

import features.Registry;

public class OpLockATest extends AbstractFeaturesTest {

  @Test
  public void testUpdateOfPrimitiveColumnFails() {
    Parent p = new Parent("parent");
    this.commitAndReOpen();

    p = this.reload(p);
    p.setName("parent2");

    // Change it first
    Jdbc.update(Registry.getDataSource(), "UPDATE parent SET name = 'changed', version = 1");

    try {
      this.commitAndReOpen();
      Assert.fail();
    } catch (OpLockException ole) {
      Assert.assertEquals("Op lock failed for Parent[1]", ole.getMessage());
    }
  }

  @Test
  public void testAdditionOfChildFails() {
    Parent p = new Parent("parent");
    this.commitAndReOpen();

    p = this.reload(p);
    new Child(p, "c");

    // Change it first
    Jdbc.update(Registry.getDataSource(), "UPDATE parent SET name = 'changed', version = 1");

    try {
      this.commitAndReOpen();
      Assert.fail();
    } catch (OpLockException ole) {
      Assert.assertEquals("Op lock failed for Parent[1]", ole.getMessage());
    }
  }

  @Test
  public void testRemovalOfChildFails() {
    Parent p = new Parent("parent");
    Child c = new Child(p, "c");
    this.commitAndReOpen();

    p = this.reload(p);
    c = this.reload(c);
    Child.queries.delete(c);

    // Change it first
    Jdbc.update(Registry.getDataSource(), "UPDATE parent SET name = 'changed', version = 1");

    try {
      this.commitAndReOpen();
      Assert.fail();
    } catch (OpLockException ole) {
      Assert.assertEquals("Op lock failed for Parent[1]", ole.getMessage());
    }
  }

  @Test
  public void testJustChangingOfChildDoesNotFail() {
    Parent p = new Parent("parent");
    Child c = new Child(p, "c");
    this.commitAndReOpen();

    p = this.reload(p);
    c = this.reload(c);
    c.setName("c2");

    // Change it first
    Jdbc.update(Registry.getDataSource(), "UPDATE parent SET name = 'changed', version = 1");

    // no OpLockException means passing
    this.commitAndReOpen();

    p = this.reload(p);
    Assert.assertEquals(1L, p.getVersion().longValue());
  }

}
