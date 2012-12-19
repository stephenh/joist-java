package joist.domain.orm.queries.columns;

import java.sql.ResultSet;
import java.sql.SQLException;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.orm.queries.Alias;

public class ByteArrayAliasColumn<T extends DomainObject> extends AliasColumn<T, byte[], byte[]> {

  public ByteArrayAliasColumn(Alias<T> alias, String name, Shim<T, byte[]> shim) {
    super(alias, name, shim);
  }

  @Override
  public byte[] toJdbcValue(ResultSet rs, int i) throws SQLException {
    return rs.getBytes(i);
  }

}
