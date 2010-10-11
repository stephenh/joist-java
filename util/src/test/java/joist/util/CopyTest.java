package joist.util;

import junit.framework.Assert;
import junit.framework.TestCase;

public class CopyTest extends TestCase {

  public void testUnique() {
    FluentList<String> l = Copy.list("one", "one", "two");
    Assert.assertEquals(Copy.list("one", "two"), l.unique());
  }

}
