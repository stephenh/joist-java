package joist.domain.orm.queries.columns;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.Where;
import joist.util.Join;

public class IntAliasColumn<T extends DomainObject> extends AliasColumn<T, Integer, Integer> {

  /** Converts the database {@code defaultValue} into the field initializer string. */
  public static String defaultValue(String defaultValue) {
    return defaultValue;
  }

  public IntAliasColumn(Alias<T> alias, String name, Shim<T, Integer> shim) {
    super(alias, name, shim);
  }

  public Where in(List<Integer> ids) {
    return new Where(this.getQualifiedName() + " in (" + Join.comma(ids) + ")");
  }

  @Override
  public Integer toJdbcValue(ResultSet rs, int i) throws SQLException {
    return rs.getInt(i);
  }

}
