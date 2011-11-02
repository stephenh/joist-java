package features.domain;

import org.junit.Assert;
import org.junit.Test;

public class ParentCTest extends AbstractFeaturesTest {

  @Test
  public void testTwoParents() {
    ParentCBar bar = new ParentCBar();
    bar.setName("bar");
    bar.setFirstParent(new ParentCFoo("foo1"));
    bar.setSecondParent(new ParentCFoo("foo2"));
    this.commitAndReOpen();

    bar = this.reload(bar);
    Assert.assertEquals("foo1", bar.getFirstParent().getName());
    Assert.assertEquals("foo2", bar.getSecondParent().getName());
    Assert.assertEquals(bar, bar.getFirstParent().getFirstParentParentCBars().get(0));
    Assert.assertEquals(bar, bar.getSecondParent().getSecondParentParentCBars().get(0));
  }

}
