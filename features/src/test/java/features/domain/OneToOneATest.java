package features.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import joist.domain.ValidationAssert;

import org.junit.Assert;
import org.junit.Test;

public class OneToOneATest extends AbstractFeaturesTest {

  @Test
  public void testOneSide() {
    OneToOneABar bar = new OneToOneABar("bar");
    OneToOneAFoo foo = new OneToOneAFoo("foo");
    bar.setOneToOneAFoo(foo);
    this.commitAndReOpen();
    bar = this.reload(bar);
    Assert.assertEquals("foo", bar.getOneToOneAFoo().getName());
  }

  @Test
  public void testOneSideTwice() {
    OneToOneAFoo foo = new OneToOneAFoo("foo");
    OneToOneABar bar1 = new OneToOneABar("bar");
    bar1.setOneToOneAFoo(foo);
    OneToOneABar bar2 = new OneToOneABar("bar");
    bar2.setOneToOneAFoo(foo);
    Assert.assertNull(bar1.getOneToOneAFoo());
  }

  @Test
  public void testManySide() {
    OneToOneABar bar = new OneToOneABar("bar");
    OneToOneAFoo foo = new OneToOneAFoo("foo");
    foo.setOneToOneABar(bar);
    this.commitAndReOpen();
    foo = this.reload(foo);
    Assert.assertEquals("bar", foo.getOneToOneABar().getName());
  }

  @Test
  public void testUniqueOneNameToo() {
    OneToOneBFoo foo = new OneToOneBFoo("foo");
    OneToOneBBar bar1 = new OneToOneBBar("bar1");
    OneToOneBBar bar2 = new OneToOneBBar("bar2");
    bar1.setOneToOneBFoo(foo);
    bar2.setOneToOneBFoo(foo);
    this.commitAndReOpen();

    bar1 = this.reload(bar1);
    Assert.assertEquals("foo", bar1.getOneToOneBFoo().getName());
    bar2 = this.reload(bar2);
    Assert.assertEquals("foo", bar2.getOneToOneBFoo().getName());
  }

  @Test
  public void testUnset() {
    OneToOneABar bar = new OneToOneABar("bar");
    OneToOneAFoo foo = new OneToOneAFoo("foo");
    bar.setOneToOneAFoo(foo);
    this.commitAndReOpen();

    bar.setOneToOneAFoo(null);
    ValidationAssert.assertErrors(bar, "One To One Afoo is required");
  }

  @Test
  public void testDeleteInOrder() {
    // bar has a fk column to foo
    OneToOneABar bar = new OneToOneABar("bar");
    OneToOneAFoo foo = new OneToOneAFoo("foo");
    bar.setOneToOneAFoo(foo);
    this.commitAndReOpen();
    // so it's okay if we delete bar first
    OneToOneABar.queries.delete(this.reload(bar));
    OneToOneAFoo.queries.delete(this.reload(foo));
  }

  @Test
  public void testDeleteOutOfOrder() {
    // bar has a fk column to foo
    OneToOneABar bar = new OneToOneABar("bar");
    OneToOneAFoo foo = new OneToOneAFoo("foo");
    bar.setOneToOneAFoo(foo);
    this.commitAndReOpen();
    // it should also be okay to delete foo first, but it means
    // queueing the delete until the flush (which joist didn't
    // always do)
    OneToOneAFoo.queries.delete(this.reload(foo));
    OneToOneABar.queries.delete(this.reload(bar));
    this.commitAndReOpen();
  }

  @Test
  public void testNoChange() {
    OneToOneABar bar = new OneToOneABar("bar");
    OneToOneAFoo foo = new OneToOneAFoo("foo");
    bar.setOneToOneAFoo(foo);
    this.commitAndReOpen();
    foo = this.reload(foo);
    bar = this.reload(bar);
    bar.setOneToOneAFoo(foo);
    assertThat(bar.getChanged().size(), is(0));
    assertThat(foo.getChanged().size(), is(0));
    foo.setOneToOneABar(bar);
    assertThat(bar.getChanged().size(), is(0));
    assertThat(foo.getChanged().size(), is(0));
  }
}
