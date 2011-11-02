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
      Assert.assertEquals("Not found", nfe.getMessage());
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
      Assert.assertEquals("Too many", nfe.getMessage());
    }
  }

}
