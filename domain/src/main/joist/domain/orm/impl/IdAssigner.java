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

/** Assigns all of the ids for new objects in our UoW in 1 call to the database. */
public class IdAssigner {

    public <T extends DomainObject> void assignIds(MapToList<Class<T>, T> byClassInserts) {
        if (byClassInserts.size() == 0) {
            return;
        }
        String allSql = this.buildSql(byClassInserts);
        if ("".equals(allSql)) {
            return;
        }
        List<Integer> ids = this.fetchIds(allSql);
        this.setIds(byClassInserts, ids);
    }

    private <T extends DomainObject> String buildSql(MapToList<Class<T>, T> byClassInserts) {
        List<String> allSql = new ArrayList<String>();
        for (Entry<Class<T>, List<T>> entry : byClassInserts.entrySet()) {
            String sql = "select nextval('" + AliasRegistry.get(entry.getKey()).getRootClassAlias().getTableName() + "_id_seq')";
            for (int i = 0; i < entry.getValue().size(); i++) {
                if (entry.getValue().get(i).getId() != null) {
                    // Skip new objects that got manually assigned an id, but assign version=0 first
                    AliasRegistry.get(entry.getKey()).getVersionColumn().setJdbcValue(entry.getValue().get(i), 0);
                    continue;
                }
                allSql.add(sql);
            }
        }
        return Join.join(allSql, " UNION ALL ");
    }

    // Get all of the nextvals at once
    private List<Integer> fetchIds(String sql) {
        final List<Integer> ids = new ArrayList<Integer>();
        Jdbc.query(UoW.getConnection(), sql, new RowMapper() {
            public void mapRow(ResultSet rs) throws SQLException {
                ids.add(rs.getInt(1));
            }
        });
        return ids;
    }

    // Assign them back to the instances in order
    private <T extends DomainObject> void setIds(MapToList<Class<T>, T> byClassInserts, List<Integer> ids) {
        int i = 0;
        for (Entry<Class<T>, List<T>> entry : byClassInserts.entrySet()) {
            Alias<T> t = AliasRegistry.get(entry.getKey());
            for (T instance : entry.getValue()) {
                int id = ids.get(i++);
                instance.setId(id);
                t.getVersionColumn().setJdbcValue(instance, 0);
            }
        }
    }

}
