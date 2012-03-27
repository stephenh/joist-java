package features.domain;

import org.junit.Test;

public class AliasRegistryTest extends AbstractFeaturesTest {

  @Test
  public void testDoesNotBlowUpOnSubClass() {
    new Parent() {
      {
        this.setName("a");
      }
    };
    this.commitAndReOpen();
  }

}
