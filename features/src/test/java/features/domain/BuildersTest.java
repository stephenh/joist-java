package features.domain;

import org.junit.Assert;
import org.junit.Test;

import features.domain.builders.ParentBuilder;

public class BuildersTest {

  @Test
  public void testEquality() {
    Parent p = new Parent();
    ParentBuilder b1 = new ParentBuilder(p);
    ParentBuilder b2 = new ParentBuilder(p);
    Assert.assertEquals(b1, b2);
  }

}
