package joist.domain.orm.queries.columns;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.orm.queries.Alias;

import com.domainlanguage.money.Money;

public class MoneyAliasColumn<T extends DomainObject> extends AliasColumn<T, Money, Long> {

  public MoneyAliasColumn(Alias<T> alias, String name, Shim<T, Money> shim) {
    super(alias, name, shim);
  }

  @Override
  public Money toDomainValue(Long jdbcValue) {
    if (jdbcValue == null) {
      return null;
    }
    return Money.dollars(jdbcValue.intValue() / 100.0);
  }

  @Override
  public Long toJdbcValue(Money domainValue) {
    if (domainValue == null) {
      return null;
    }
    return domainValue.getAmount().multiply(new BigDecimal(100)).toBigIntegerExact().longValue();
  }

  @Override
  public Long toJdbcValue(ResultSet rs, int i) throws SQLException {
    return rs.getLong(i);
  }

}
