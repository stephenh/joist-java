package features.domain;

import org.junit.Assert;
import org.junit.Test;

public class BetweenTest extends AbstractFeaturesTest {

  @Test
  public void testBetween() {
    Primitives foo = new Primitives("testSave");
    foo.setId(5l);
    this.commitAndReOpen();
    Assert.assertEquals(1, Primitives.queries.findByIdBetween(4l, 6l).size());
    Assert.assertEquals(1, Primitives.queries.findByIdBetween(5l, 6l).size());
    Assert.assertEquals(1, Primitives.queries.findByIdBetween(4l, 5l).size());
    Assert.assertEquals(1, Primitives.queries.findByIdBetween(5l, 5l).size());
    Assert.assertEquals(0, Primitives.queries.findByIdBetween(3l, 4l).size());
    Assert.assertEquals(0, Primitives.queries.findByIdBetween(6l, 7l).size());
    Assert.assertEquals(0, Primitives.queries.findByIdBetween(null, 6l).size());
    Assert.assertEquals(0, Primitives.queries.findByIdBetween(null, null).size());
  }

}
