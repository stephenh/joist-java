package features.domain;

import static features.domain.builders.Builders.aParentD;
import static joist.util.Copy.list;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import features.domain.builders.ParentDBuilder;
import features.domain.builders.ParentDChildABuilder;

public class ChildCollectionSkippedTest extends AbstractFeaturesTest {

  @Test
  public void testCollectionIsSkipped() {
    ParentDBuilder p = aParentD().defaults();
    List<String> methodNames = list();
    for (Method m : p.get().getClass().getMethods()) {
      methodNames.add(m.getName());
    }
    Assert.assertFalse(methodNames.contains("getParentDChildAs"));
    Assert.assertTrue(methodNames.contains("getParentDChildBs"));
  }

  @Test
  public void testFindIdsMethodExists() {
    ParentDBuilder p = aParentD().defaults();
    ParentDChildABuilder a = p.newParentDChildA().defaults();
    this.commitAndReOpen();
    Assert.assertEquals(a.id(), ParentD.queries.findParentDChildAsIds(p.get()).get(0));
  }

  @Test
  public void testBuilderNewMethodExists() {
    ParentDBuilder p = aParentD().defaults();
    p.newParentDChildA().defaults();
    p.newParentDChildB().defaults();
    this.commitAndReOpen();
  }

  @Test
  public void testBuilderCollectionMethodExists() {
    ParentDBuilder p = aParentD().defaults();
    ParentDChildABuilder a = p.newParentDChildA().defaults();
    this.commitAndReOpen();
    Assert.assertEquals(a, p.parentDChildAs().get(0));
    Assert.assertEquals(a, p.parentDChildA(0));
  }
}
