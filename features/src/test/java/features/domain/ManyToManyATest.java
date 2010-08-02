package features.domain;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

public class ManyToManyATest extends AbstractFeaturesTest {

  public void testViaExplicitFooToBar() {
    ManyToManyAFoo foo = new ManyToManyAFoo();
    foo.setName("foo");
    ManyToManyABar bar = new ManyToManyABar();
    bar.setName("bar");

    ManyToManyAFooToBar join = new ManyToManyAFooToBar();
    join.setManyToManyAFoo(foo);
    join.setManyToManyABar(bar);
    this.commitAndReOpen();

    foo = this.reload(foo);
    Assert.assertEquals("bar", foo.getManyToManyAFooToBars().get(0).getManyToManyABar().getName());
  }

  public void testViaImplicitFooToBar() {
    ManyToManyAFoo foo = new ManyToManyAFoo();
    foo.setName("foo");
    ManyToManyABar bar = new ManyToManyABar();
    bar.setName("bar");

    foo.addManyToManyABar(bar);
    this.commitAndReOpen();

    foo = this.reload(foo);
    Assert.assertEquals("bar", foo.getManyToManyAFooToBars().get(0).getManyToManyABar().getName());
    bar = this.reload(bar);
    foo.removeManyToManyABar(bar);
    Assert.assertEquals(0, foo.getManyToManyAFooToBars().size());
    this.commitAndReOpen();

    foo = this.reload(foo);
    Assert.assertEquals(0, foo.getManyToManyAFooToBars().size());
  }

  public void testViaImplicitFooToBarWithSetter() {
    ManyToManyAFoo foo = new ManyToManyAFoo();
    foo.setName("foo");
    ManyToManyABar bar = new ManyToManyABar();
    bar.setName("bar");

    List<ManyToManyABar> bars = new ArrayList<ManyToManyABar>();
    bars.add(bar);
    foo.setManyToManyABars(bars);
    this.commitAndReOpen();

    foo = this.reload(foo);
    Assert.assertEquals(1, foo.getManyToManyAFooToBars().size());
    Assert.assertEquals("bar", foo.getManyToManyAFooToBars().get(0).getManyToManyABar().getName());

    bar = this.reload(bar);
    bars.clear();
    foo.setManyToManyABars(bars);
    Assert.assertEquals(0, foo.getManyToManyAFooToBars().size());
    this.commitAndReOpen();

    foo = this.reload(foo);
    Assert.assertEquals(0, foo.getManyToManyAFooToBars().size());
  }

}
