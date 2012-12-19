package joist.domain.orm.queries.columns;

import java.sql.ResultSet;
import java.sql.SQLException;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.Where;

public class StringAliasColumn<T extends DomainObject> extends AliasColumn<T, String, String> {

  /** Converts the database {@code defaultValue} into the field initializer string. */
  public static String defaultValue(String defaultValue) {
    // postgresql returns 'foo'::character varying
    if (defaultValue.endsWith("::character varying")) {
      defaultValue = defaultValue.substring(1, defaultValue.length() - "'::character varying".length());
    }
    return "\"" + defaultValue + "\"";
  }

  public StringAliasColumn(Alias<T> alias, String name, Shim<T, String> shim) {
    super(alias, name, shim);
  }

  public Where like(String pattern) {
    return new Where(this.getQualifiedName() + " like ?", pattern);
  }

  @Override
  public String toJdbcValue(ResultSet rs, int i) throws SQLException {
    return rs.getString(i);
  }

}
