package joist.domain.orm;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

public class IdentityMapTest {

  @Test
  public void failsAfterLimitIsHit() {
    IdentityMap m = new IdentityMap();
    for (int i = 0; i < IdentityMap.SIZE_LIMIT - 1; i++) {
      m.store(new DummyDomainObject((long) i));
    }
    try {
      m.store(new DummyDomainObject((long) IdentityMap.SIZE_LIMIT));
      fail();
    } catch (IllegalStateException ise) {
      assertThat(ise.getMessage(), is("IdentityMap grew over the 10000 instance limit"));
    }
  }

}
