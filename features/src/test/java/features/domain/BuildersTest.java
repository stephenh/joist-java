package features.domain;

import junit.framework.Assert;
import junit.framework.TestCase;
import features.domain.builders.ParentBuilder;

public class BuildersTest extends TestCase {

  public void testEquality() {
    Parent p = new Parent();
    ParentBuilder b1 = new ParentBuilder(p);
    ParentBuilder b2 = new ParentBuilder(p);
    Assert.assertEquals(b1, b2);
  }

}
