package features.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

public class DomainObjectTest extends AbstractFeaturesTest {

  @Test
  public void testIdCannotChange() {
    Parent p = new Parent("parent");
    this.commitAndReOpen();
    p = this.reload(p);
    try {
      p.setId(10l);
      fail();
    } catch (IllegalStateException ise) {
      assertThat(ise.getMessage(), is("Parent[1] id cannot be changed"));
    }
  }

}
