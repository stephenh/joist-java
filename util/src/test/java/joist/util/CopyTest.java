package joist.util;

import org.junit.Assert;
import org.junit.Test;

public class CopyTest {

  @Test
  public void testUnique() {
    FluentList<String> l = Copy.list("one", "one", "two");
    Assert.assertEquals(Copy.list("one", "two"), l.unique());
  }

}
