package features.domain;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ParentBTest extends AbstractFeaturesTest {

  @Test
  public void testLoadChildren() {
    ParentBParent p = new ParentBParent();
    p.setName("parent");

    ParentBChildFoo f = new ParentBChildFoo();
    f.setName("foo");
    f.setParentBParent(p);

    ParentBChildBar b = new ParentBChildBar();
    b.setName("bar");
    b.setParentBParent(p);
    this.commitAndReOpen();

    List<ParentBChildFoo> foos = ParentBChildFoo.queries.findByParentName("parent");
    Assert.assertEquals("foo", foos.get(0).getName());
  }

}
