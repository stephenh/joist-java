package features.domain;

import static features.domain.builders.Builders.aValuesA;

import org.junit.Test;

public class DropNotNullTest extends AbstractFeaturesTest {

  @Test
  public void testNotRequiredAnyMore() {
    aValuesA().name("a").i(null);
    this.commitAndReOpen();
  }

}
