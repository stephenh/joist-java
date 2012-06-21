package joist.domain.orm.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import joist.domain.DomainObject;
import joist.domain.orm.AliasRegistry;
import joist.domain.orm.queries.Alias;
import joist.domain.uow.UoW;
import joist.jdbc.Jdbc;
import joist.jdbc.RowMapper;
import joist.util.Join;
import joist.util.MapToList;

public class SequenceIdAssigner {

  public <T extends DomainObject> void assignIds(MapToList<Class<T>, T> byClassInserts) {
    if (byClassInserts.size() == 0) {
      return;
    }
    List<String> allSql = this.buildSql(byClassInserts);
    if (allSql.size() == 0) {
      return;
    }
    // If we have too many new objects, postgresql will fail to parse the
    // enormously long SQL statement, so batch them in chunks of 1000
    List<Long> ids = new ArrayList<Long>();
    int taken = 0;
    while (ids.size() < allSql.size()) {
      int take = Math.min(allSql.size() - taken, 1000);
      ids.addAll(this.fetchIds(allSql.subList(taken, taken + take)));
      taken = take;
    }
    this.setIds(byClassInserts, ids);
  }

  private <T extends DomainObject> List<String> buildSql(MapToList<Class<T>, T> byClassInserts) {
    List<String> allSql = new ArrayList<String>();
    for (Entry<Class<T>, List<T>> entry : byClassInserts.entrySet()) {
      String sql = "select nextval('" + AliasRegistry.get(entry.getKey()).getRootClassAlias().getTableName() + "_id_seq')";
      for (T instance : entry.getValue()) {
        if (instance.getId() != null) {
          // Skip new objects that got manually assigned an id, but assign version=0 first
          AliasRegistry.get(entry.getKey()).getVersionColumn().setJdbcValue(instance, 0l);
          continue;
        }
        allSql.add(sql);
      }
    }
    return allSql;
  }

  // Get all of the nextvals at once
  private List<Long> fetchIds(List<String> sql) {
    final List<Long> ids = new ArrayList<Long>();
    Jdbc.query(UoW.getConnection(), Join.join(sql, " UNION ALL "), new RowMapper() {
      public void mapRow(ResultSet rs) throws SQLException {
        ids.add(rs.getLong(1));
      }
    });
    return ids;
  }

  // Assign them back to the instances in order
  private <T extends DomainObject> void setIds(MapToList<Class<T>, T> byClassInserts, List<Long> ids) {
    int i = 0;
    for (Entry<Class<T>, List<T>> entry : byClassInserts.entrySet()) {
      Alias<T> t = AliasRegistry.get(entry.getKey());
      for (T instance : entry.getValue()) {
        if (instance.getId() == null) {
          instance.setId(ids.get(i++));
          t.getVersionColumn().setJdbcValue(instance, 0l);
        }
      }
    }
  }
}
