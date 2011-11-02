package features.domain;

import org.junit.Assert;
import org.junit.Test;

import com.domainlanguage.time.CalendarDate;

public class UserTypeATest extends AbstractFeaturesTest {

  @Test
  public void testLoadChildren() {
    UserTypesAFoo foo = new UserTypesAFoo();
    foo.setName("foo");
    foo.setCreated(CalendarDate.from(2005, 1, 1));
    this.commitAndReOpen();

    foo = this.reload(foo);
    Assert.assertEquals(CalendarDate.from(2005, 1, 1), foo.getCreated());

    foo.setCreated(CalendarDate.from(2005, 1, 2));
    this.commitAndReOpen();

    foo = this.reload(foo);
    Assert.assertEquals(CalendarDate.from(2005, 1, 2), foo.getCreated());
  }

  @Test
  public void testQuery() {
    UserTypesAFoo foo = new UserTypesAFoo();
    foo.setName("foo");
    foo.setCreated(CalendarDate.from(2005, 1, 1));
    this.commitAndReOpen();
    Assert.assertEquals("foo", UserTypesAFoo.queries.findByCreated(CalendarDate.from(2005, 1, 1)).getName());
  }

}
