package joist.domain.orm;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

public class IdentityMapTest {

  @Test
  public void failsAfterLimitIsHit() {
    int oldSizeLimit = IdentityMap.getSizeLimit();
    try {
      IdentityMap.setSizeLimit(10);
      IdentityMap m = new IdentityMap();
      for (int i = 1; i < 10; i++) {
        m.store(new DummyDomainObject((long) i));
      }
      try {
        m.store(new DummyDomainObject(10l));
        fail();
      } catch (IllegalStateException ise) {
        assertThat(ise.getMessage(), is("IdentityMap grew over the 10 instance limit"));
      }
    } finally {
      IdentityMap.setSizeLimit(oldSizeLimit);
    }
  }

}
