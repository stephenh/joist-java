package features.domain;

import static features.domain.builders.Builders.aPrimitivesB;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class PrimitivesBAggregatesTest extends AbstractFeaturesTest {

  @Test
  public void testSumSmallInt() {
    aPrimitivesB().small1((short) 10).defaults();
    this.commitAndReOpen();
    assertThat(PrimitivesB.queries.sumShort(), is((short) 10));
  }

  @Test
  public void testCountSmallInt() {
    aPrimitivesB().small1((short) 10).defaults();
    this.commitAndReOpen();
    assertThat(PrimitivesB.queries.countShort(), is(1L));
  }
}
