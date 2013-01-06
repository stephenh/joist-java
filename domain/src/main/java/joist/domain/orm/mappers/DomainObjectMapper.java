package joist.domain.orm.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import joist.domain.DomainObject;
import joist.domain.orm.IdentityMap;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.uow.UoW;
import joist.jdbc.RowMapper;

public class DomainObjectMapper<T extends DomainObject> implements RowMapper {

  private static ConcurrentHashMap<Class<?>, Map<Class<?>, Integer>> offsetCache = new ConcurrentHashMap<Class<?>, Map<Class<?>, Integer>>();
  private final Alias<T> from;
  private final List<T> results;
  private final IdentityMap cache = UoW.getIdentityMap();

  public DomainObjectMapper(Alias<T> from, List<T> results) {
    this.from = from;
    this.results = results;
  }

  public void mapRow(ResultSet rs) throws SQLException {
    Long id = new Long(rs.getLong(this.from.getIdColumn().getName()));
    T instance = (T) this.cache.findOrNull(this.from.getDomainRootClass(), id);

    if (instance == null) {
      Alias<? extends T> childAlias = this.getChildAlias(rs);
      instance = newInstance(childAlias);
      this.hydrate(childAlias, instance, rs);
      this.cache.store(instance);
    }

    this.results.add(instance);
  }

  private Alias<? extends T> getChildAlias(ResultSet rs) throws SQLException {
    if (this.from.getSubClassAliases().size() == 0) {
      return this.from;
    } else {
      int offset = rs.getInt("_clazz");
      return (offset == -1) ? this.from : (Alias<T>) this.from.getSubClassAliases().get(offset);
    }
  }

  private void hydrate(Alias<? extends T> childAlias, T instance, ResultSet rs) throws SQLException {
    Alias<? super T> current = (Alias<? super T>) childAlias;
    while (current != null) {
      int i = DomainObjectMapper.getOffset(this.from, current);
      for (AliasColumn<? super T, ?, ?> c : current.getColumns()) {
        if (rs.getObject(i + 1) != null) {
          AliasColumn<T, ?, Object> column = (AliasColumn<T, ?, Object>) c;
          column.setJdbcValue(instance, column.toJdbcValue(rs, i + 1));
        } else {
          c.setJdbcValue(instance, null);
        }
        i++;
      }
      current = current.getBaseClassAlias();
    }
  }

  @SuppressWarnings("cast")
  private static <T> T newInstance(Alias<? extends T> alias) {
    try {
      return (T) alias.getDomainClass().newInstance();
    } catch (IllegalAccessException iae) {
      throw new RuntimeException(iae);
    } catch (InstantiationException ie) {
      throw new RuntimeException(ie);
    }
  }

  /** @return the offset from the select query for {@code current} given that {@code from} was the primary alias. */
  private static <T extends DomainObject> int getOffset(Alias<T> from, Alias<? super T> current) {
    Map<Class<?>, Integer> offsets = DomainObjectMapper.offsetCache.get(from.getDomainClass());
    if (offsets == null) {
      offsets = DomainObjectMapper.makeOffsets(from);
      DomainObjectMapper.offsetCache.put(from.getDomainClass(), offsets);
    }
    return offsets.get(current.getDomainClass());
  }

  private static <T extends DomainObject> Map<Class<?>, Integer> makeOffsets(Alias<T> from) {
    int i = 0;
    Map<Class<?>, Integer> offsets = new HashMap<Class<?>, Integer>();
    // add base classes, with the root first
    for (Alias<? super T> base : DomainObjectMapper.getBaseClassAliases(from)) {
      offsets.put(base.getDomainClass(), i);
      i += base.getColumns().size();
    }
    // then the 'from' alias itself
    offsets.put(from.getDomainClass(), i);
    i += from.getColumns().size();
    // finally all of the sub class aliases (already linearized)
    for (Alias<? extends T> sub : from.getSubClassAliases()) {
      offsets.put(sub.getDomainClass(), i);
      i += sub.getColumns().size();
    }
    return offsets;
  }

  /** @return all of the base class aliases of {@code from}, with the root alias first. */
  private static <T extends DomainObject> List<Alias<? super T>> getBaseClassAliases(Alias<T> from) {
    List<Alias<? super T>> bases = new ArrayList<Alias<? super T>>();
    Alias<? super T> current = from.getBaseClassAlias();
    while (current != null) {
      bases.add(0, current);
      current = current.getBaseClassAlias();
    }
    return bases;
  }

}
