package features.domain;

import static features.domain.builders.Builders.aParent;
import static features.domain.builders.Builders.aParentF;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import joist.domain.exceptions.TooManyException;
import joist.domain.orm.queries.Select;

import org.junit.Test;

import features.domain.builders.ChildBuilder;
import features.domain.builders.ParentBuilder;
import features.domain.builders.ParentFBuilder;

public class SelectTest extends AbstractFeaturesTest {

  @Test
  public void testUniqueOrNullWithNone() {
    ParentAlias p = new ParentAlias();
    Parent found = Select.from(p).where(p.name.eq("foo")).uniqueOrNull();
    assertThat(found, is(nullValue()));
  }

  @Test
  public void testUniqueOrNullWithOne() {
    new Parent("parent");
    this.commitAndReOpen();

    ParentAlias p = new ParentAlias();
    Parent found = Select.from(p).where(p.name.eq("parent")).uniqueOrNull();
    assertThat(found.getName(), is("parent"));
  }

  @Test(expected = TooManyException.class)
  public void testUniqueOrNullWithTooMany() {
    new Parent("parent");
    new Parent("parent");
    this.commitAndReOpen();

    ParentAlias p = new ParentAlias();
    Select.from(p).where(p.name.eq("parent")).uniqueOrNull();
  }

  @Test
  public void testJoin() {
    ParentBuilder parent = aParent().name("p").defaults();
    ChildBuilder child = parent.newChild().defaults();
    this.commitAndReOpen();

    ChildAlias c = new ChildAlias();
    ParentAlias p = new ParentAlias();
    Child c1 = Select //
      .from(c)
      .join(p.on(c.parent))
      .where(p.name.eq("p"))
      .uniqueOrNull();
    assertThat(c1, is(child.get()));
  }

  @Test
  public void testLeftJoin() {
    ParentFBuilder parent = aParentF().defaults();
    this.commitAndReOpen();

    ParentFAlias p = new ParentFAlias();
    ChildFAlias c = new ChildFAlias();
    ParentF p1 = Select //
      .from(p)
      .join(c.leftOn(p.childTwo))
      .where(c.id.isNull().or(c.name.eq("c2")))
      .uniqueOrNull();
    assertThat(p1, is(parent.get()));
  }
}
