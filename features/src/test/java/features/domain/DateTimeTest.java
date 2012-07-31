package features.domain;

import static features.domain.builders.Builders.aValuesB;
import static features.domain.builders.Builders.theValuesB;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class DateTimeTest extends AbstractFeaturesTest {

  @Test
  public void testNullDatetime() {
    aValuesB().name("foo");
    this.commitAndReOpen();
    assertThat(theValuesB(1).start(), is(nullValue()));
  }

}
