package joist.domain.orm.queries.columns;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import joist.domain.orm.DummyDomainObject;

import org.junit.Test;

import com.domainlanguage.money.Money;

public class MoneyAliasColumnTest {

  @Test
  public void toDomainValue() {
    assertDomainValue(0, Money.dollars(0.00));
    assertDomainValue(100, Money.dollars(1.00));
    assertDomainValue(151, Money.dollars(1.51));
  }

  @Test
  public void toJdbcValue() {
    assertJdbcValue(Money.dollars(0.00), 0);
    assertJdbcValue(Money.dollars(1.00), 100);
    assertJdbcValue(Money.dollars(1.51), 151);
  }

  private static void assertDomainValue(long jdbcValue, Money domainValue) {
    Money actualValue = new MoneyAliasColumn<DummyDomainObject>(null, null, null).toDomainValue(jdbcValue);
    assertThat(actualValue, is(domainValue));
  }

  private static void assertJdbcValue(Money domainValue, long jdbcValue) {
    long actualValue = new MoneyAliasColumn<DummyDomainObject>(null, null, null).toJdbcValue(domainValue);
    assertThat(actualValue, is(jdbcValue));
  }

}
