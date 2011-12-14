package features.domain;

import joist.domain.exceptions.NotFoundException;
import joist.domain.exceptions.TooManyException;

import org.junit.Assert;
import org.junit.Test;

public class PrimitivesSelectUniqueTest extends AbstractFeaturesTest {

  @Test
  public void testNoneFound() {
    try {
      Primitives.queries.findByName("none");
      Assert.fail();
    } catch (NotFoundException nfe) {
      Assert.assertEquals("Instance of Primitives not found", nfe.getMessage());
    }
  }

  @Test
  public void testNoneFoundWithId() {
    try {
      Primitives.queries.find(1);
      Assert.fail();
    } catch (NotFoundException nfe) {
      Assert.assertEquals("Primitives#1 not found", nfe.getMessage());
    }
  }

  @Test
  public void testTooManyFound() {
    new Primitives("p1").flag(false);
    new Primitives("p2").flag(false);
    this.commitAndReOpen();

    try {
      Primitives.queries.findByFlagValue(false);
      Assert.fail();
    } catch (TooManyException nfe) {
      Assert.assertEquals("Too many Primitives found: [Primitives[1], Primitives[2]]", nfe.getMessage());
    }
  }

}
