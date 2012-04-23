package features.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import joist.domain.exceptions.TooManyException;
import joist.domain.orm.queries.Select;

import org.junit.Test;

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

}
