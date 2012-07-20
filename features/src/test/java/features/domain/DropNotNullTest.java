package features.domain;

import static features.domain.builders.Builders.aValuesA;
import static features.domain.builders.Builders.theValuesA;
import static joist.domain.ValidationAssert.assertErrors;
import static joist.domain.ValidationAssert.assertValid;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import joist.domain.uow.UoW;
import joist.jdbc.Jdbc;

import org.junit.Test;

import features.domain.builders.ValuesABuilder;

public class DropNotNullTest extends AbstractFeaturesTest {

  @Test
  public void testDroppedNotNullIsNotRequiredAnyMore() {
    ValuesABuilder a = aValuesA().name("a").i(null).j(2);
    assertValid(a);
  }

  @Test
  public void testDroppedNotNullStillDefaults() {
    Jdbc.update(UoW.getConnection(), "INSERT INTO values_a (name, version) VALUES ('a', 0);");
    this.commitAndReOpen();
    assertThat(theValuesA(1).i(), is(1));
  }

  @Test
  public void testAddedNotNullIsNowRequired() {
    ValuesABuilder a = aValuesA().name("a").i(null).j(null);
    assertErrors(a, "J is required");
  }

  @Test
  public void testAddedNotNullStillDefaults() {
    Jdbc.update(UoW.getConnection(), "INSERT INTO values_a (name, version) VALUES ('a', 0);");
    this.commitAndReOpen();
    assertThat(theValuesA(1).j(), is(2));
  }
}
