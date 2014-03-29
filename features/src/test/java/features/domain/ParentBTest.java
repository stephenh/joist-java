package features.domain;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import features.domain.builders.Builders;
import features.domain.builders.ParentBChildZazBuilder;
import features.domain.builders.ParentBParentBuilder;

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

  @Test
  public void testBuilderDoesNotCreateDuplicateParents() {
    // defaults should fill in both zaz+it's sibling with only 1 parent
    ParentBChildZazBuilder z = Builders.aParentBChildZaz().defaults();
    this.commitAndReOpen();
    Assert.assertEquals(z.parentBParent(), z.parentBChildBar().parentBParent());
  }

  @Test
  public void testBuilderUsesExistingParent() {
    // given zaz is using an existing parent
    ParentBParentBuilder p = Builders.aParentBParent().defaults();
    // defaults should use zaz's parent for the sibling
    ParentBChildZazBuilder z = p.newParentBChildZaz().defaults();
    this.commitAndReOpen();
    Assert.assertEquals(p, z.parentBParent());
    Assert.assertEquals(p, z.parentBChildBar().parentBParent());
  }

}
