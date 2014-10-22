package features.domain;

import static features.domain.builders.Builders.aPrimitivesC;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.domainlanguage.time.CalendarDate;

import features.domain.queries.PrimitivesQueries.NameAndFlag;

public class PrimitivesAggregatesTest extends AbstractFeaturesTest {

  @Test
  public void testCount() {
    new Primitives("count");
    this.commitAndReOpen();
    Assert.assertEquals(1, Primitives.queries.count());
  }

  @Test
  public void testCountWithConditions() {
    new Primitives("count");
    this.commitAndReOpen();

    Assert.assertEquals(1, Primitives.queries.countWhereFlagIs(false));
    Assert.assertEquals(0, Primitives.queries.countWhereFlagIs(true));
  }

  @Test
  public void testNameOnly() {
    new Primitives("testNameOnly");
    this.commitAndReOpen();
    Assert.assertEquals("testNameOnly", Primitives.queries.findNameOnly(1));
  }

  @Test
  public void testNamesOnly() {
    new Primitives("testNamesOnly1");
    new Primitives("testNamesOnly2");
    this.commitAndReOpen();
    List<String> names = Primitives.queries.findNamesOnly();
    Assert.assertEquals("testNamesOnly1", names.get(0));
    Assert.assertEquals("testNamesOnly2", names.get(1));
  }

  @Test
  public void testUserTypeOnly() {
    CalendarDate jan1 = CalendarDate.date(2000, 1, 1);
    aPrimitivesC().day(jan1).defaults();
    this.commitAndReOpen();
    List<CalendarDate> days = PrimitivesC.queries.findDatesOnly();
    Assert.assertEquals(jan1, days.get(0));
  }

  @Test
  public void testNameAndFlagOnly() {
    new Primitives("name1");
    new Primitives("name2");
    this.commitAndReOpen();

    List<NameAndFlag> dtos = Primitives.queries.findNameAndFlagOnly();
    Assert.assertEquals(2, dtos.size());
    Assert.assertEquals("name1", dtos.get(0).name);
    Assert.assertEquals("name2", dtos.get(1).name);
  }

}
