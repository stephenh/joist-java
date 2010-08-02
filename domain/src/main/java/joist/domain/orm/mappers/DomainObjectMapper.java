package joist.domain.orm.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import joist.domain.DomainObject;
import joist.domain.orm.IdentityMap;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.uow.UoW;
import joist.jdbc.RowMapper;

public class DomainObjectMapper<T extends DomainObject> implements RowMapper {

  private final Alias<T> from;
  private final List<T> results;
  private final IdentityMap cache = UoW.getIdentityMap();

  public DomainObjectMapper(Alias<T> from, List<T> results) {
    this.from = from;
    this.results = results;
  }

  public void mapRow(ResultSet rs) throws SQLException {
    Integer id = new Integer(rs.getInt(this.from.getIdColumn().getName()));
    T instance = (T) this.cache.findOrNull(this.from.getDomainRootClass(), id);

    if (instance == null) {
      instance = this.newInstance(rs);
      this.hydrate(instance, rs);
      this.cache.store(instance);
    }

    this.results.add(instance);
  }

  private void hydrate(T instance, ResultSet rs) throws SQLException {
    Alias<? super T> current;
    if (this.from.getSubClassAliases().size() == 0) {
      current = this.from;
    } else {
      int offset = rs.getInt("_clazz");
      if (offset == -1) {
        current = this.from;
      } else {
        current = (Alias<T>) this.from.getSubClassAliases().get(offset);
      }
    }
    while (current != null) {
      for (AliasColumn<? super T, ?, ?> c : current.getColumns()) {
        // Object jdbcValue = rs.getObject(c.getQualifiedName());
        Object jdbcValue = rs.getObject(c.getName());
        ((AliasColumn<T, ?, Object>) c).setJdbcValue(instance, jdbcValue);
      }
      current = current.getBaseClassAlias();
    }
  }

  private T newInstance(ResultSet rs) throws SQLException {
    if (this.from.getSubClassAliases().size() > 0) {
      int offset = rs.getInt("_clazz");
      if (offset == -1) {
        return this.newInstance(this.from);
      } else {
        return this.newInstance(this.from.getSubClassAliases().get(offset));
      }
    }
    return this.newInstance(this.from);
  }

  private T newInstance(Alias<? extends T> alias) {
    try {
      return alias.getDomainClass().newInstance();
    } catch (IllegalAccessException iae) {
      throw new RuntimeException(iae);
    } catch (InstantiationException ie) {
      throw new RuntimeException(ie);
    }
  }

}
