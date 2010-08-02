package features.domain;

import junit.framework.Assert;

public class InheritanceCTest extends AbstractFeaturesTest {

  public void testFoo1() {
    InheritanceCFoo1 f1 = new InheritanceCFoo1();
    f1.setName("f1");
    f1.setFoo("f1");
    this.commitAndReOpen();

    f1 = (InheritanceCFoo1) InheritanceC.queries.find(1);
    Assert.assertEquals("f1", f1.getName());
    Assert.assertEquals("f1", f1.getFoo());
  }

  public void ignoreForNowTestFoo2() {
    InheritanceCFoo2 f2 = new InheritanceCFoo2();
    f2.setName("f2");
    f2.setFoo("f2");
    this.commitAndReOpen();

    f2 = (InheritanceCFoo2) InheritanceC.queries.find(1);
    Assert.assertEquals("f2", f2.getName());
    Assert.assertEquals("f2", f2.getFoo());
  }

  public void testJustC() {
    InheritanceC c = new InheritanceC();
    c.setName("c");
    this.commitAndReOpen();

    c = InheritanceC.queries.find(1);
    Assert.assertEquals("c", c.getName());
  }

}
