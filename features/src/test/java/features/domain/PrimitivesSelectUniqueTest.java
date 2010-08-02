package features.domain;

import joist.domain.Requirements;
import joist.domain.exceptions.NotFoundException;
import joist.domain.exceptions.TooManyException;
import junit.framework.Assert;

public class PrimitivesSelectUniqueTest extends AbstractFeaturesTest {

  public void testNoneFound() {
    Requirements.selectUniqueWithNoResultsFails.tests();
    try {
      Primitives.queries.findByName("none");
      Assert.fail();
    } catch (NotFoundException nfe) {
      Assert.assertEquals("Not found", nfe.getMessage());
    }
  }

  public void testTooManyFound() {
    new Primitives("p");
    new Primitives("p");
    this.commitAndReOpen();

    Requirements.selectUniqueWithTooManyFails.tests();
    try {
      Primitives.queries.findByName("p");
      Assert.fail();
    } catch (TooManyException nfe) {
      Assert.assertEquals("Too many", nfe.getMessage());
    }
  }

}
