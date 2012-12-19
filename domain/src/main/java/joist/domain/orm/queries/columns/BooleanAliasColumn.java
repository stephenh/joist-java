package joist.domain.orm.queries.columns;

import java.sql.ResultSet;
import java.sql.SQLException;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.SetItem;
import joist.domain.uow.UoW;

public class BooleanAliasColumn<T extends DomainObject> extends AliasColumn<T, Boolean, Boolean> {

  /** Converts the database {@code defaultValue} into the field initializer string. */
  public static String defaultValue(String defaultValue) {
    // look for 0/1 in bit/tinyint fields
    if ("0".equals(defaultValue) || "b'0'".equals(defaultValue)) {
      return "false";
    } else if ("1".equals(defaultValue) || "b'1'".equals(defaultValue)) {
      return "true";
    } else {
      return new Boolean(defaultValue).toString();
    }
  }

  public BooleanAliasColumn(Alias<T> alias, String name, Shim<T, Boolean> shim) {
    super(alias, name, shim);
  }

  public SetItem<T> to(Boolean value) {
    Object o = UoW.getDb().isMySQL() ? (value ? 1 : 0) : value;
    return new SetItem<T>(this, o);
  }

  @Override
  public Boolean toJdbcValue(ResultSet rs, int i) throws SQLException {
    return rs.getBoolean(i);
  }
}
