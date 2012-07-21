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
    // a and i used to be not null
    ValuesABuilder a = aValuesA().name("a").a(null).b("b").i(null).j(2);
    assertValid(a);
  }

  @Test
  public void testDroppedNotNullStillDefaults() {
    Jdbc.update(UoW.getConnection(), "INSERT INTO values_a (name, version) VALUES ('a', 0);");
    this.commitAndReOpen();
    assertThat(theValuesA(1).i(), is(1));
    assertThat(theValuesA(1).a(), is("a"));
  }

  @Test
  public void testAddedNotNullIsNowRequired() {
    // b and j used to be nullable
    ValuesABuilder a = aValuesA().name("a").a(null).b(null).i(null).j(null);
    assertErrors(a, "B is required", "J is required");
  }

  @Test
  public void testAddedNotNullStillDefaults() {
    Jdbc.update(UoW.getConnection(), "INSERT INTO values_a (name, version) VALUES ('a', 0);");
    this.commitAndReOpen();
    assertThat(theValuesA(1).j(), is(2));
    assertThat(theValuesA(1).b(), is("b"));
  }
}
