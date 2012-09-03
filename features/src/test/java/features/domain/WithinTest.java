package features.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.domainlanguage.time.CalendarDate;
import com.domainlanguage.time.CalendarInterval;

public class WithinTest extends AbstractFeaturesTest {

  private static CalendarDate jan(int day) {
    return CalendarDate.from(2012, 1, day);
  }

  @Test
  public void testBetween() {
    UserTypesAFoo foo = new UserTypesAFoo();
    foo.setName("foo");
    foo.setCreated(jan(5));
    this.commitAndReOpen();
    assertThat(UserTypesAFoo.queries.findByCreatedWithin(jan(4).through(jan(6))).size(), is(1));
    assertThat(UserTypesAFoo.queries.findByCreatedWithin(jan(5).through(jan(6))).size(), is(1));
    assertThat(UserTypesAFoo.queries.findByCreatedWithin(jan(4).through(jan(5))).size(), is(1));
    assertThat(UserTypesAFoo.queries.findByCreatedWithin(jan(5).through(jan(5))).size(), is(1));
    assertThat(UserTypesAFoo.queries.findByCreatedWithin(jan(3).through(jan(4))).size(), is(0));
    assertThat(UserTypesAFoo.queries.findByCreatedWithin(jan(6).through(jan(7))).size(), is(0));
    assertThat(UserTypesAFoo.queries.findByCreatedWithin(CalendarInterval.inclusive(jan(4), null)).size(), is(1));
    assertThat(UserTypesAFoo.queries.findByCreatedWithin(CalendarInterval.inclusive(null, jan(6))).size(), is(1));
    assertThat(UserTypesAFoo.queries.findByCreatedWithin(CalendarInterval.inclusive(null, null)).size(), is(1));
    assertThat(UserTypesAFoo.queries.findByCreatedWithin(CalendarInterval.inclusive(jan(6), null)).size(), is(0));
    assertThat(UserTypesAFoo.queries.findByCreatedWithin(CalendarInterval.inclusive(null, jan(4))).size(), is(0));
  }

}
