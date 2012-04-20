package features.domain;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ManyToManyATest extends AbstractFeaturesTest {

  @Test
  public void testViaExplicitFooToBar() {
    ManyToManyAFoo foo = new ManyToManyAFoo("foo");
    ManyToManyABar bar = new ManyToManyABar("bar");

    ManyToManyAFooToBar join = new ManyToManyAFooToBar();
    join.setManyToManyAFoo(foo);
    join.setManyToManyABar(bar);
    this.commitAndReOpen();

    foo = this.reload(foo);
    Assert.assertEquals("bar", foo.getManyToManyABars().get(0).getName());
  }

  @Test
  public void testViaImplicitFooToBar() {
    ManyToManyAFoo foo = new ManyToManyAFoo("foo");
    ManyToManyABar bar = new ManyToManyABar("bar");
    foo.addManyToManyABar(bar);
    this.commitAndReOpen();

    foo = this.reload(foo);
    Assert.assertEquals("bar", foo.getManyToManyABars().get(0).getName());
    bar = this.reload(bar);
    foo.removeManyToManyABar(bar);
    Assert.assertEquals(0, foo.getManyToManyABars().size());
    this.commitAndReOpen();

    foo = this.reload(foo);
    Assert.assertEquals(0, foo.getManyToManyABars().size());
  }

  @Test
  public void testViaImplicitFooToBarWithSetter() {
    ManyToManyAFoo foo = new ManyToManyAFoo("foo");
    ManyToManyABar bar = new ManyToManyABar("bar");

    List<ManyToManyABar> bars = new ArrayList<ManyToManyABar>();
    bars.add(bar);
    foo.setManyToManyABars(bars);
    this.commitAndReOpen();

    foo = this.reload(foo);
    Assert.assertEquals(1, foo.getManyToManyABars().size());
    Assert.assertEquals("bar", foo.getManyToManyABars().get(0).getName());

    bar = this.reload(bar);
    bars.clear();
    foo.setManyToManyABars(bars);
    Assert.assertEquals(0, foo.getManyToManyABars().size());
    this.commitAndReOpen();

    foo = this.reload(foo);
    Assert.assertEquals(0, foo.getManyToManyABars().size());
  }

  @Test
  public void testDeleteFooSide() {
    ManyToManyAFoo foo = new ManyToManyAFoo("foo");
    ManyToManyABar bar = new ManyToManyABar("bar");
    foo.addManyToManyABar(bar);
    this.commitAndReOpen();

    foo = this.reload(foo);
    bar = this.reload(bar);
    ManyToManyAFoo.queries.delete(foo);
    Assert.assertEquals(true, bar.getChanged().hasManyToManyAFooToBars());
    this.commitAndReOpen();

    Assert.assertEquals(0, ManyToManyAFoo.queries.count());
    Assert.assertEquals(0, ManyToManyAFooToBar.queries.count());
  }

  @Test
  public void testDeleteBarSide() {
    ManyToManyAFoo foo = new ManyToManyAFoo("foo");
    ManyToManyABar bar = new ManyToManyABar("bar");
    foo.addManyToManyABar(bar);
    this.commitAndReOpen();

    foo = this.reload(foo);
    bar = this.reload(bar);
    ManyToManyABar.queries.delete(bar);
    Assert.assertEquals(true, foo.getChanged().hasManyToManyAFooToBars());
    this.commitAndReOpen();

    Assert.assertEquals(0, ManyToManyABar.queries.count());
    Assert.assertEquals(0, ManyToManyAFooToBar.queries.count());
  }

}
